package com.example.domain.repo;

import com.example.domain.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ParkingLotRepo extends JpaRepository<ParkingLot, Long> {
    List<ParkingLot> findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(float startLat, float endLat, float startLng, float endLng);

    List<ParkingLot> findAllBylatitudeGreaterThanAndLatitudeLessThan(float startLat, float endLat);
}
