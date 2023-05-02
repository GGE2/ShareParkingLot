package com.example.jumoparking.service.impl;

import com.example.domain.dto.DaySaveDto;
import com.example.domain.entity.DayData;
import com.example.domain.entity.ShareLot;
import com.example.domain.repo.DayDataRepo;
import com.example.domain.repo.ShareLotRepo;
import com.example.jumoparking.service.DayDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DayDataServiceImpl implements DayDataService {

    private final DayDataRepo dayDataRepo;
    private final ShareLotRepo shareLotRepo;
    @Override
    @Transactional
    public boolean saveDayData(DaySaveDto daySaveDto, Long lotId) {
        Optional<ShareLot> shareLot = shareLotRepo.findById(lotId);
        DayData dayData = DayData.builder(daySaveDto, shareLot.get()).build();
        dayDataRepo.save(dayData);
        dayDataRepo.flush();
        return true;
    }

    @Override
    @Transactional
    public void deleteDayData(Long lotId) {
        Optional<ShareLot> shareLot = shareLotRepo.findById(lotId);
        List<DayData> dayDataList = shareLot.get().getDayDataList();
        dayDataRepo.deleteAllInBatch(dayDataList);
    }
}
