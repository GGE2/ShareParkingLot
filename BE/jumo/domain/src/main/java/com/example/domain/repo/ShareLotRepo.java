package com.example.domain.repo;

import com.example.domain.entity.ShareLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareLotRepo extends JpaRepository<ShareLot, Long> {

}
