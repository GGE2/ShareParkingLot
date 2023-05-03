package com.example.jumoparking.service.impl;

import com.example.domain.dto.ParkingBottomListDto;
import com.example.domain.dto.ParkingDetailDto;
import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.domain.entity.Favorite;
import com.example.domain.entity.ParkingLot;
import com.example.domain.entity.ShareLot;
import com.example.domain.repo.FavoriteRepo;
import com.example.domain.repo.ParkingLotRepo;
import com.example.domain.repo.ShareLotRepo;
import com.example.domain.repo.UserRepo;
import com.example.jumoparking.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepo parkingLotRepo;

    private final ShareLotRepo shareLotRepo;

    private final FavoriteRepo favoriteRepo;

    private final UserRepo userRepo;

    @Override
    public List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto) {
        float zoom = parkingInDto.getZoomLevel();

        List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
        List<ShareLot> shareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());

        if (zoom >= 13.8 && 15> zoom && parkingLots.size() + shareLots.size() > 7)
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

                if (zoom >= 13.8 && 14.2 > zoom){
                    for (int i = oldAddrs.length -1 ; i >=1 ; i--){
                        checkStr = oldAddrs[i];
                        if (checkStr.endsWith("읍") || checkStr.endsWith("면") || checkStr.endsWith("시") || checkStr.endsWith("구")){
                            break;
                        }
                    }
                }
                else{
                    for (int i = oldAddrs.length -1 ; i >=1 ; i--){
                        checkStr = oldAddrs[i];
                        if (checkStr.endsWith("동") || checkStr.endsWith("리") || checkStr.endsWith("가") || checkStr.endsWith("길")){
                            break;
                        }
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
            return parkingLotList;
        }
        else if ((zoom >= 15 && zoom < 17.2) || (zoom >= 13.8 && 15 > zoom && parkingLots.size() + shareLots.size() <= 7))
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
}
