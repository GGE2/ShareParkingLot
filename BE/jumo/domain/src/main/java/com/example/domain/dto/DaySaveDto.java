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
    private int shareLotId;
    @Enumerated(EnumType.STRING)
    private DayName day_str;

    private int day_start;

    private int day_end;
}