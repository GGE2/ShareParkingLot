package com.example.domainparking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favorite_id;

    @ManyToOne
    @JoinColumn(name="sha_id")
    private ShareLot shareLot;

    @ManyToOne
    @JoinColumn(name="lot_id")
    private ParkingLot parkingLot;

//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private User user;
}
