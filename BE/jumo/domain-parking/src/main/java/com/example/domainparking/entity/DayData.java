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
public class DayData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long day_id;

    private String day_str;

    private String day_start;

    private String day_end;

}
