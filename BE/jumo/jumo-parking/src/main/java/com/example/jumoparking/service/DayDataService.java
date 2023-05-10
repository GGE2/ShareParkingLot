package com.example.jumoparking.service;

import com.example.domain.dto.parking.DaySaveDto;

import java.util.List;

public interface DayDataService {
    boolean saveDayData(DaySaveDto daySaveDto, Long lotId);

    void deleteDayData(Long lotId);

    List<DaySaveDto> listDayData(Long parkId);

    void updateDayData(DaySaveDto daySaveDto, Long lotId);
}
