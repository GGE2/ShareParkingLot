package com.example.domain.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long favorite_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sha_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ShareLot shareLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lot_id",nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ParkingLot parkingLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
