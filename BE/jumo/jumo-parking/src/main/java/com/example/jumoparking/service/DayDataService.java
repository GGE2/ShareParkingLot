package com.example.jumoparking.service;

import com.example.domain.dto.DaySaveDto;
import com.example.domain.entity.DayData;

public interface DayDataService {
    boolean saveDayData(DaySaveDto daySaveDto, Long lotId);

    void deleteDayData(Long lotId);
}
