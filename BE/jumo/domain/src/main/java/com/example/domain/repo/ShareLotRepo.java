package com.example.domain.repo;

import com.example.domain.entity.ShareLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareLotRepo extends JpaRepository<ShareLot, Long> {
    List<ShareLot> findAllByLatitudeGreaterThanAndLatitudeLessThanAndLongitudeGreaterThanAndLongitudeLessThan(float startLat, float endLat, float startLng, float endLng);

    List<ShareLot> findShareLotsByUser_UserId(Long userId);
}
