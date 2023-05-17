package com.example.domain.repo;

import com.example.domain.entity.DayData;
import com.example.domain.entity.User;
import com.example.domain.etc.DayName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DayDataRepo extends JpaRepository<DayData, Long> {
    Optional<DayData> findDayDataByShareLot_ShaIdAndDayStrEquals(Long shaId, DayName dayStr);
}
