package com.example.domain.dto.point.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TypeResponseDto {
    private boolean oneHour;
    private boolean threeHours;
    private boolean fiveHours;
    private boolean allDay;
}
