package com.example.domain.repo;

import com.example.domain.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
}
