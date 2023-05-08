package com.example.jumoparking.service;


import com.example.domain.dto.ParkingBottomListDto;
import com.example.domain.dto.ParkingDetailDto;
import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;

import java.util.List;

public interface ParkingLotService {
    List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto);

    List<ParkingBottomListDto> getBottomListOfPoint(ParkingInDto parkingInDto);

    ParkingDetailDto getDetail(Long parkId);

    boolean checkFavorite(Long userId, Long lotId);

    List<ParkingBottomListDto> getFavoriteList(Long userId);

    List<ParkingListDto> getListOfParking(ParkingInDto parkingInDto);


    List<ParkingListDto> getListOfShare(ParkingInDto parkingInDto);
}
