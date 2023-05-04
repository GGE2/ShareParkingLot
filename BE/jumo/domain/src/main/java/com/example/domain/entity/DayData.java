package com.example.domain.entity;

import javax.persistence.*;

import com.example.domain.etc.DayName;

import com.example.domain.dto.DaySaveDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "DayDataBuilder")
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
    private DayName dayStr;

    @Column(name = "day_start")
    private int day_start;

    @Column(name = "day_end")
    private int day_end;

    @Column(name = "enable")
    private boolean enable;

    @Builder
    public static DayDataBuilder builder(DaySaveDto daySaveDto, ShareLot shareLot){
        return DayDataBuilder()
                .shareLot(shareLot)
                .dayStr(daySaveDto.getDayStr())
                .day_start(daySaveDto.getDayStart())
                .day_end(daySaveDto.getDayEnd())
                .enable(daySaveDto.isEnable());
    }

    public void updateDayData(DaySaveDto daySaveDto){
        this.day_end = daySaveDto.getDayEnd();
        this.day_start = daySaveDto.getDayStart();
        this.dayStr = daySaveDto.getDayStr();
        this.enable = daySaveDto.isEnable();
    }
}
