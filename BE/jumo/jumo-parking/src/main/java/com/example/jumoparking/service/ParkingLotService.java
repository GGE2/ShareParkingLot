package com.example.jumoparking.service;


import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;

import java.util.List;

public interface ParkingLotService {
    List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto);
}
