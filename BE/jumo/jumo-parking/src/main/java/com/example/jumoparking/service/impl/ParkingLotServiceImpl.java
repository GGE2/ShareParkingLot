package com.example.jumoparking.service.impl;

import com.example.domain.dto.ParkingBottomListDto;
import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.domain.entity.ParkingLot;
import com.example.domain.entity.ShareLot;
import com.example.domain.repo.ParkingLotRepo;
import com.example.domain.repo.ShareLotRepo;
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

    @Override
    public List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto) {
        float zoom = parkingInDto.getZoomLevel();

        if (zoom > 13.8 && 15> zoom) {
            HashMap<String, Float> latMap = new HashMap<String, Float>();
            HashMap<String, Float> lngMap = new HashMap<String, Float>();
            HashMap<String, Integer> cntMap = new HashMap<String, Integer>();

            List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                    parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());

            for (ParkingLot parkinglot : parkingLots) {
                String oldAddr = parkinglot.getOld_addr();
                String[] oldAddrs = oldAddr.split(" ");
                String checkStr = "";

                for (int a = 1; a < oldAddrs.length; a++) {
                    checkStr = oldAddrs[oldAddrs.length - a];
                    if (checkStr.endsWith("동") || checkStr.endsWith("가") || checkStr.endsWith("읍") ||
                            checkStr.endsWith("리")) {
                        break;
                    }
                }

                if (latMap.containsKey(checkStr)) {
                    latMap.put(checkStr, latMap.get(checkStr) + parkinglot.getLatitude());
                    lngMap.put(checkStr, lngMap.get(checkStr) + parkinglot.getLongitude());
                    cntMap.put(checkStr, cntMap.get(checkStr) + 1);
                } else {
                    latMap.put(checkStr, parkinglot.getLatitude());
                    lngMap.put(checkStr, parkinglot.getLongitude());
                    cntMap.put(checkStr, 1);
                }
            }

            HashMap<String, Float> latAvg = new HashMap<String, Float>();
            HashMap<String, Float> lngAvg = new HashMap<String, Float>();

            List<String> oneCntList = new ArrayList<>();
            for (String s : latMap.keySet()){
                int cnt =
            }
            List<ParkingListDto> parkingLotList = new ArrayList<>();

            parkingLotList.add(new ParkingListDto(lat_sum/cnt, lng_sum/cnt, 0,0,cnt));
            return parkingLotList;
        }
        else if ( zoom > 15 && zoom < 17.2)
        {
            List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                    parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
            List<ShareLot> shareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                    parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng()
            );

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
}
