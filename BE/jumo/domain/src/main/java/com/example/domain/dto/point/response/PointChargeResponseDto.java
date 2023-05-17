package com.example.domain.dto.point.response;

import com.example.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor

public class PointChargeResponseDto {
    private Long userId;
    private String nickname;
    private int ptCharge;
    private int ptHas;

    @Builder
    public PointChargeResponseDto(User user, int ptCharge) {
        this.userId = user.getUserId();
        this.nickname = user.getName();
        this.ptCharge = ptCharge;
        this.ptHas = user.getPtHas();
    }

}
