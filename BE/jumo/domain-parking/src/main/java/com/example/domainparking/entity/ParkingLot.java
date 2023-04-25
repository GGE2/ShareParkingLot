package com.example.domainparking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lot_id;

    private String lot_part;

    private String lot_type;

    private String lot_name;

    private String road_addr;

    private String old_addr;

    private float latitude;

    private float longitude;

    private String open_day;

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

}
