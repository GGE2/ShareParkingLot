package com.example.jumouser.controller;

import com.example.domain.dto.point.response.*;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.service.PointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
@Api(tags = "포인트 로직")
public class PointController {

    private final PointService pointService;
    private final UserRepo userRepo;

    // 현재 포인트 조회 (완료)
    @ApiOperation(value = "현재 포인트 조회", notes = "정수값 리턴")
    @GetMapping("/{userId}")
    public int getUserPoint(@PathVariable Long userId) {
        return pointService.getUserPoint(userId);
    }

    // 월별 포인트 획득 내역 조회 (완료)
    @ApiOperation(value = "월별 포인트 획득 내역 조회", notes = "YYYY MM 형식 / 5월 -> 05로 입력")
    @GetMapping("/earned/{userId}/{year}/{month}")
    public List<PointEarnedResponseDto> getEarnedPointList(@PathVariable Long userId, @PathVariable String year, @PathVariable String month) {
        return pointService.getEarnedPointList(userId, year, month);
    }

    // 월별 포인트 사용 내역 조회 (완료)
    @ApiOperation(value = "월별 포인트 사용 내역 조회", notes = "YYYY MM 형식 / 5월 -> 05로 입력")
    @GetMapping("/spent/{userId}/{year}/{month}")
    public List<PointSpentResponseDto> getSpentPointList(@PathVariable Long userId, @PathVariable String year, @PathVariable String month) {
        return pointService.getSpentPointList(userId, year, month);
    }

    // 포인트 충전 (완료)
    @ApiOperation(value = "포인트 충전", notes = "")
    @PutMapping("/charge/{userId}/{ptCharge}")
    public PointChargeResponseDto chargePoint(@PathVariable Long userId, @PathVariable int ptCharge) {
        return pointService.chargePoint(userId, ptCharge);
    }
}
