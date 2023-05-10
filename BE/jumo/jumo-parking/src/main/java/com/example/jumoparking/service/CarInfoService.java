package com.example.jumoparking.service;

import com.example.domain.dto.parking.CarInfoDto;
import com.example.domain.dto.parking.CarSaveDto;

import java.util.List;

public interface CarInfoService {
    List<CarInfoDto> getListCarInfo(Long userId);

    void saveCarInfo(CarSaveDto carSaveDto, Long userId);

    boolean checkRep(Long carId, Long userId);
}
