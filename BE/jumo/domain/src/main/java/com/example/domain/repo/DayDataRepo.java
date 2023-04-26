package com.example.domain.repo;

import com.example.domain.entity.DayData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayDataRepo extends JpaRepository<DayData, Long> {
}
