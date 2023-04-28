package com.example.domain.entity;

import javax.persistence.*;

import com.example.domain.etc.DayName;
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
    @Column(name = "day_id")
    private Long day_id;

    @ManyToOne
    @JoinColumn(name = "sha_id")
    private ShareLot shareLot;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_str")
    private DayName day_str;

    @Column(name = "day_start")
    private int day_start;

    @Column(name = "day_end")
    private int day_end;

}
