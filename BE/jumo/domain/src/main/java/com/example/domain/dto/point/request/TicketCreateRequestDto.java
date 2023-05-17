package com.example.domain.dto.point.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCreateRequestDto {
    private Long shaId;
    private String carNumber;
    private int inTiming;
    private int type;
}
