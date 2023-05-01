package com.example.domain.repo;

import com.example.domain.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
    Favorite findFavoritesByParkingLot_LotIdAndUser_UserId(Long lotId, Long userId);
    Favorite findFavoritesByShareLot_ShaIdAndUser_UserId(Long lotId, Long userId);

    List<Favorite> findFavoritesByUser_UserId(Long userId);
}
