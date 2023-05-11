package com.example.jumoparking.service;


import com.example.domain.dto.parking.ParkingBottomListDto;
import com.example.domain.dto.parking.ParkingDetailDto;
import com.example.domain.dto.parking.ParkingInDto;
import com.example.domain.dto.parking.ParkingListDto;

import java.util.List;

public interface ParkingLotService {
    List<ParkingListDto> getListOfPoint(ParkingInDto parkingInDto);

    List<ParkingBottomListDto> getBottomListOfPoint(ParkingInDto parkingInDto);

    ParkingDetailDto getDetail(Long parkId,Long userId);

    boolean checkFavorite(Long userId, Long lotId);

    List<ParkingBottomListDto> getFavoriteList(Long userId);

    List<ParkingListDto> getListOfParking(ParkingInDto parkingInDto);

    List<ParkingBottomListDto> getOrderByDist(ParkingInDto parkingInDto);

    List<ParkingBottomListDto> getOrderByPrice(ParkingInDto parkingInDto);


    List<ParkingListDto> getListOfShare(ParkingInDto parkingInDto);
}
