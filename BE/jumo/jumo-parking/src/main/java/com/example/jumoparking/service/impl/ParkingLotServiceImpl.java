package com.example.jumoparking.service.impl;

import com.example.domainparking.entity.ParkingLot;
import com.example.domainparking.repo.ParkingLotRepo;
import com.example.jumoparking.dto.ParkingInDto;
import com.example.jumoparking.dto.ParkingListDto;
import com.example.jumoparking.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepo parkingLotRepo;
    @Override
    public List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto) {
        List<ParkingLot> parkingLots = parkingLotRepo.findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(
                parkingInDto.getStartLat(), parkingInDto.getEndLat(), parkingInDto.getStartLng(), parkingInDto.getEndLng());
        System.out.println(parkingLots.size());
        return parkingLots.stream().map(parkingLot -> new ParkingListDto(parkingLot)).collect(Collectors.toList());
    }
}
