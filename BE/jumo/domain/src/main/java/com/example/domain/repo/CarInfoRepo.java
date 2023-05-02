package com.example.domain.repo;

import com.example.domain.entity.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarInfoRepo extends JpaRepository<CarInfo, Long> {
    List<CarInfo> findCarInfosByUser_UserId(Long userId);


}
