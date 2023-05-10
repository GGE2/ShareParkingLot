package com.example.jumoparking.service.impl;

import com.example.domain.dto.parking.ParkingBottomListDto;
import com.example.domain.dto.parking.ParkingDetailDto;
import com.example.domain.dto.parking.ParkingInDto;
import com.example.domain.dto.parking.ParkingListDto;
import com.example.domain.entity.*;
import com.example.domain.etc.DayName;
import com.example.domain.etc.OutTiming;
import com.example.domain.repo.*;
import com.example.jumoparking.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepo parkingLotRepo;

    private final ShareLotRepo shareLotRepo;

    private final FavoriteRepo favoriteRepo;

    private final UserRepo userRepo;

    private final DayDataRepo dayDataRepo;

    private final OutTiming outTiming;

    @Override
    public List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto) {
        float zoom = parkingInDto.getZoomLevel();
        // 위도 경도 기반 주차장 리스트들 얻어오기
        List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
        List<ShareLot> originShareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());

        // 운영 요일 기반 공유주차장 거르기 로직
        List<ShareLot> shareLots = filteringByDay(originShareLots);

        //클러스터링 로직 메소드로 분리
        return clusteringCoord(zoom, shareLots, parkingLots);
    }

    private  List<ShareLot> filteringByDay(List<ShareLot> originShareLots){
        return originShareLots.stream().filter(shareLot -> {
            DayName dayOfWeek = DayName.valueOf(LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US));
            Optional <DayData> dayData= dayDataRepo.findDayDataByShareLot_ShaIdAndDayStrEquals(shareLot.getShaId(), dayOfWeek);

            System.out.println(dayData.get().getDayStr());

            if(dayData.get().isEnable()){
                int [] enableTimeList = new int[24];
                Arrays.fill(enableTimeList, 0);
                int nowHour = LocalDateTime.now().getHour();
                System.out.println(nowHour);
                System.out.println(dayData.get().getDay_end() );
                if (dayData.get().getDay_end() <= nowHour ){
                    return false;
                }else{
                    for(int i=Math.max(nowHour+1, dayData.get().getDay_start()); i < dayData.get().getDay_end() ; i++){
                        enableTimeList[i] = 1;
                    }
                    List<Ticket> ticketList = shareLot.getTicketList();
                    ticketList.stream().map(ticket -> {
                        int endTime = outTiming.OutTimingMethod(ticket.getIn_timing(), ticket.getType());
                        for (int j= ticket.getIn_timing() ; j<endTime; j++){
                            enableTimeList[j] = 1;
                        }
                        return null;
                    });
                    System.out.println(enableTimeList);

                    for(int i = 0 ; i< 24; i++){
                        if (enableTimeList[i] == 1){
                            return true;
                        }
                    }
                    return false;
                }
            }
            else{
                return false;
            }
        }).collect(Collectors.toList());
    }

    private List<ParkingListDto> clusteringCoord(float zoom, List<ShareLot> shareLots, List<ParkingLot> parkingLots){
        if (zoom >= 13.8 && 15> zoom)
        {
            HashMap<String,Float> latMap = new HashMap<String,Float>();
            HashMap<String,Float> lngMap = new HashMap<String,Float>();
            HashMap<String,Integer> cntMap = new HashMap<String,Integer>();
            HashMap<String,Float> latAvg = new HashMap<String,Float>();
            HashMap<String,Float> lngAvg = new HashMap<String,Float>();

            for (ParkingLot parkinglot: parkingLots) {
                String oldAddr = parkinglot.getOld_addr();
                String[] oldAddrs = oldAddr.split(" ");
                String checkStr = new String();

                for (int i = oldAddrs.length -1 ; i >=1 ; i--){
                    checkStr = oldAddrs[i];
                    if (checkStr.endsWith("동") || checkStr.endsWith("리") || checkStr.endsWith("가") || checkStr.endsWith("길")|| checkStr.endsWith("읍") || checkStr.endsWith("면") || checkStr.endsWith("시") || checkStr.endsWith("구")){
                        break;
                    }
                }

                if (latMap.containsKey(checkStr)){
                    latMap.put(checkStr, latMap.get(checkStr) + parkinglot.getLatitude());
                    lngMap.put(checkStr, lngMap.get(checkStr) + parkinglot.getLongitude());
                    cntMap.put(checkStr, cntMap.get(checkStr) +1);
                }
                else{
                    latMap.put(checkStr,  parkinglot.getLatitude());
                    lngMap.put(checkStr,  parkinglot.getLongitude());
                    cntMap.put(checkStr, 1);
                }
            }

            for (ShareLot shareLot: shareLots) {
                String jibun = shareLot.getSha_jibun();
                String[] jibuns = jibun.split(" ");
                String checkStr = new String();

                for (int i = jibuns.length -1 ; i >=1 ; i--){
                    checkStr = jibuns[i];
                    if (checkStr.endsWith("동") || checkStr.endsWith("리") || checkStr.endsWith("가") || checkStr.endsWith("길")){
                        break;
                    }
                }

                if (latMap.containsKey(checkStr)){
                    latMap.put(checkStr, latMap.get(checkStr) + shareLot.getLatitude());
                    lngMap.put(checkStr, lngMap.get(checkStr) + shareLot.getLongitude());
                    cntMap.put(checkStr, cntMap.get(checkStr) +1);
                }
                else{
                    latMap.put(checkStr,  shareLot.getLatitude());
                    lngMap.put(checkStr,  shareLot.getLongitude());
                    cntMap.put(checkStr, 1);
                }
            }
            List<ParkingListDto> parkingLotList = new ArrayList<>();
            List<String> cntOneList = new ArrayList<>();

            for(String s : latMap.keySet()){
                float lat_sum = latMap.get(s);
                float lng_sum = lngMap.get(s);
                int cnt = cntMap.get(s);

                if(cnt == 1)
                {
                    cntOneList.add(s);
                }
                else {
                    latAvg.put(s, lat_sum/cnt);
                    lngAvg.put(s, lng_sum/cnt);
                }
            }

            if (latAvg.size() != 0){
                for (String s: cntOneList)
                {
                    String plusKey = new String();
                    double minDistance = 10000.000;
                    for (String s2 : latAvg.keySet()){
                        double tempDistance = Math.pow(Math.abs(latAvg.get(s2) - latMap.get(s)), 2) + Math.pow(Math.abs(lngAvg.get(s2) - lngMap.get(s)), 2);
                        if (tempDistance < minDistance){
                            minDistance = tempDistance;
                            plusKey = s2;
                        }
                    }

                    latAvg.put(plusKey, (cntMap.get(plusKey) * latAvg.get(plusKey) + latMap.get(s))/(cntMap.get(plusKey) + 1));
                    lngAvg.put(plusKey, (cntMap.get(plusKey) * lngAvg.get(plusKey) + lngMap.get(s))/(cntMap.get(plusKey) + 1));
                    cntMap.put(plusKey, cntMap.get(plusKey)+1);
                }
                for(String s : latMap.keySet()){
                    if ( cntMap.get(s) != 1)
                    {
                        parkingLotList.add(new ParkingListDto(-1L, latMap.get(s)/cntMap.get(s), lngMap.get(s)/cntMap.get(s), 0,0,cntMap.get(s)));
                    }
                }
            }
            else{
                for(String s : latMap.keySet()) {
                    parkingLotList.add(new ParkingListDto(-1L, latMap.get(s), lngMap.get(s), 0, 0, cntMap.get(s)));
                }
            }



            return parkingLotList;
        }
        else if (zoom >= 15 && zoom < 17.2)
        {
            List<ParkingListDto> parkList = parkingLots.stream().map(parkingLot -> new ParkingListDto(parkingLot)).collect(Collectors.toList());
            List<ParkingListDto> shaList = shareLots.stream().map(shareLot -> new ParkingListDto(shareLot)).collect(Collectors.toList());

            parkList.addAll(shaList);
            return parkList;
        }

        return new ArrayList<>();
    }

    @Override
    public List<ParkingBottomListDto> getBottomListOfPoint(ParkingInDto parkingInDto) {
        List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
        List<ShareLot> shareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng()
        );

        List<ParkingBottomListDto> parkList = parkingLots.stream().map(parkingLot -> new ParkingBottomListDto(parkingLot)).collect(Collectors.toList());
        List<ParkingBottomListDto> shaList = shareLots.stream().map(shareLot -> new ParkingBottomListDto(shareLot)).collect(Collectors.toList());

        parkList.addAll(shaList);
        return parkList;
    }

    @Override
    public ParkingDetailDto getDetail(Long parkId) {
        return new ParkingDetailDto(parkingLotRepo.findById(parkId).get());
    }

    @Override
    public boolean checkFavorite(Long userId, Long lotId) {
        Favorite favorite = favoriteRepo.findFavoritesByParkingLot_LotIdAndUser_UserId(lotId, userId);

        if (favorite == null){
            Favorite newFavorite = Favorite.builder()
                    .shareLot(null)
                    .parkingLot(parkingLotRepo.findById(lotId).get())
                    .user(userRepo.findById(userId).get())
                    .build();

            favoriteRepo.save(newFavorite);
            return true;
        }
        else{
            favoriteRepo.delete(favorite);
            return false;
        }
    }

    @Override
    public List<ParkingBottomListDto> getFavoriteList(Long userId) {
        List<Favorite> favorites = favoriteRepo.findFavoritesByUser_UserId(userId);
        List<ParkingBottomListDto> favoriteList = new ArrayList<>();
        for(Favorite favorite : favorites){
            if (favorite.getParkingLot() == null){
                favoriteList.add(new ParkingBottomListDto(favorite.getShareLot()));
            }
            else{
                favoriteList.add(new ParkingBottomListDto(favorite.getParkingLot()));
            }
        }
        return favoriteList;
    }

    @Override
    public List<ParkingListDto> getListOfParking(ParkingInDto parkingInDto) {
        float zoom = parkingInDto.getZoomLevel();
        // 위도 경도 기반 주차장 리스트들 얻어오기
        List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
        List<ShareLot> shareLots = new ArrayList<>();
        return clusteringCoord(zoom, shareLots, parkingLots);
    }

    private static int distance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return (int) Math.floor(dist); //단위 meter
    }

    private static double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }
    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }

    class ParkingBottomListDtoDistComparator implements Comparator<ParkingBottomListDto> {
        @Override
        public int compare(ParkingBottomListDto f1, ParkingBottomListDto f2) {
            if (f1.getMeter() > f2.getMeter()) {
                return 1;
            } else if (f1.getMeter() < f2.getMeter()) {
                return -1;
            }
            return 0;
        }
    }

    class ParkingBottomListDtoPriceComparator implements Comparator<ParkingBottomListDto> {
        @Override
        public int compare(ParkingBottomListDto f1, ParkingBottomListDto f2) {
            if (f1.getFeeBasic() > f2.getFeeBasic()) {
                return 1;
            } else if (f1.getFeeBasic() < f2.getFeeBasic()) {
                return -1;
            }
            return 0;
        }
    }

    @Override
    public List<ParkingBottomListDto> getOrderByDist(ParkingInDto parkingInDto) {
        List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
        List<ShareLot> shareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng()
        );

        List<ParkingBottomListDto> parkList = parkingLots.stream().map(parkingLot -> {
            int meter = distance(parkingInDto.getCenterLat(),parkingInDto.getCenterLng(), parkingLot.getLatitude(), parkingLot.getLongitude());
            return new ParkingBottomListDto(parkingLot, meter);
        }).collect(Collectors.toList());
        List<ParkingBottomListDto> shaList = shareLots.stream().map(shareLot -> {
            int meter = distance(parkingInDto.getCenterLat(),parkingInDto.getCenterLng(), shareLot.getLatitude(), shareLot.getLongitude());
            return new ParkingBottomListDto(shareLot, meter);
        }).collect(Collectors.toList());

        parkList.addAll(shaList);
        Collections.sort(parkList, new ParkingBottomListDtoDistComparator());

        return parkList;
    }




    @Override
    public List<ParkingBottomListDto> getOrderByPrice(ParkingInDto parkingInDto) {
        List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
        List<ShareLot> shareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng()
        );

        List<ParkingBottomListDto> parkList = parkingLots.stream().map(parkingLot -> {
            int meter = distance(parkingInDto.getCenterLat(),parkingInDto.getCenterLng(), parkingLot.getLatitude(), parkingLot.getLongitude());
            return new ParkingBottomListDto(parkingLot, meter);
        }).collect(Collectors.toList());
        List<ParkingBottomListDto> shaList = shareLots.stream().map(shareLot -> {
            int meter = distance(parkingInDto.getCenterLat(),parkingInDto.getCenterLng(), shareLot.getLatitude(), shareLot.getLongitude());
            return new ParkingBottomListDto(shareLot, meter);
        }).collect(Collectors.toList());

        parkList.addAll(shaList);
        Collections.sort(parkList, new ParkingBottomListDtoPriceComparator());

        return parkList;
    }

    @Override
    public List<ParkingListDto> getListOfShare(ParkingInDto parkingInDto) {
        float zoom = parkingInDto.getZoomLevel();
        // 위도 경도 기반 주차장 리스트들 얻어오기
        List<ParkingLot> parkingLots = new ArrayList<>();
        List<ShareLot> originShareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
        System.out.println(originShareLots.size());
        List<ShareLot> shareLots = filteringByDay(originShareLots);
        System.out.println(shareLots.size());
        return clusteringCoord(zoom, shareLots, parkingLots);
    }


}
