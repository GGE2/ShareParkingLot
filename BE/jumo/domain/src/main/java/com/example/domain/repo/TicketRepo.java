package com.example.domain.repo;

import com.example.domain.entity.ShareLot;
import com.example.domain.entity.Ticket;
import com.example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByBuyerAndParkingDate(User buyer, String date);
    List<Ticket> findAllByShareLotAndSellerIdAndParkingDate(ShareLot shareLot, Long sellerId, String date);

    List<Ticket> findAllByShareLotAndParkingDate(ShareLot shareLot, String date);
}
