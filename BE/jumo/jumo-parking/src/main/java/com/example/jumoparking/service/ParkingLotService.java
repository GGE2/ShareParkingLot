package com.example.jumoparking.service;


import com.example.jumoparking.dto.ParkingInDto;
import com.example.jumoparking.dto.ParkingListDto;

import java.util.List;

public interface ParkingLotService {
    List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto);
}
