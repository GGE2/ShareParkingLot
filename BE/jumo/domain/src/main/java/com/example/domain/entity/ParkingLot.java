package com.example.domain.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id")
    private Long lotId;

    private String lot_part;

    private String lot_type;

    private String lot_name;

    private String road_addr;

    private String old_addr;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    private String open_day;

    private int lot_field;

    private int week_start;

    private int week_end;

    private int sat_start;

    private int sat_end;

    private int holi_start;

    private int holi_end;

    private String fee_data;

    private int fee_basic;

    private int per_basic;

    private int plus_time;

    private int per_plus;

    private int per_day;

    private int per_month;

    private String pay_type;

    private String special_prop;

    @Builder.Default
    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL)
    private List<Favorite> favoriteList = new ArrayList<>();

}
