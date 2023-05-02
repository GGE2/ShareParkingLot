package com.example.domain.dto;

import com.example.domain.entity.DayName;
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
    private Long shareLotId;
    @Enumerated(EnumType.STRING)
    private DayName dayStr;

    private int dayStart;

    private int dayEnd;
}
