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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepo parkingLotRepo;

    private final ShareLotRepo shareLotRepo;

    @Override
    public List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto) {
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

    @Override
    public List<ParkingBottomListDto> getBottomListOfPoint(ParkingInDto parkingInDto) {
        List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
        List<ShareLot> shareLots = shareLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng()
        );

        List<ParkingListDto> parkList = parkingLots.stream().map(parkingLot -> new ParkingListDto(parkingLot)).collect(Collectors.toList());
        List<ParkingListDto> shaList = shareLots.stream().map(shareLot -> new ParkingListDto(shareLot)).collect(Collectors.toList());

        parkList.addAll(shaList);
    }
}
