package com.example.jumouser.service;


import com.example.domain.dto.point.response.*;

import java.util.List;

public interface PointService {

    // 현재 포인트 조회
    int getUserPoint(Long userId);

    // 월별 포인트 획득 내역 조회
    List<PointEarnedResponseDto> getEarnedPointList(Long userId, String year, String month);

    // 월별 포인트 사용 내역 조회
    List<PointSpentResponseDto> getSpentPointList(Long userId, String year, String month);

    // 포인트 충전
    PointChargeResponseDto chargePoint(Long userId, int ptCharge);

}
