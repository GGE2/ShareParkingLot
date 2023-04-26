package com.example.jumoparking.controller;

import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.jumoparking.service.ParkingLotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "지도 기본", description = "지도 좌표들 API")
@RestController
@RequestMapping("/parkingLot")
@RequiredArgsConstructor
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    @PostMapping("/list")
    @Operation(summary = "지도 좌표들 얻기", description = "화면의 시작 위도 끝 위도 시작 경도 끝 경도 중간 좌표, 줌 레밸을 통해 원하는 정보를 얻을 수 있게 함, 전체 필드 들 다 float")
    public List<ParkingListDto> getParkingList(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.getListOfPoint(parkingInDto);
    }


}
