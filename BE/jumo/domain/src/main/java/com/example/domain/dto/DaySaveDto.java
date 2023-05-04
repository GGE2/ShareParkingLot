package com.example.domain.dto;

import com.example.domain.entity.DayData;
import com.example.domain.etc.DayName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DaySaveDto {
    @Enumerated(EnumType.STRING)
    private DayName dayStr;

    private int dayStart;

    private int dayEnd;

    private boolean enable;

    @Builder
    public DaySaveDto(DayData dayData){
        this.dayStart = dayData.getDay_start();
        this.dayEnd = dayData.getDay_end();
        this.dayStr = dayData.getDayStr();
        this.enable = dayData.isEnable();
    }
}
