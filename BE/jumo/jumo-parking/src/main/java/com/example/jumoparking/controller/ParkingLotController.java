package com.example.jumoparking.controller;

import com.example.domain.dto.ParkingBottomListDto;
import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.jumoparking.service.ParkingLotService;
import com.example.jumoparking.service.ShareLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingLot")
@RequiredArgsConstructor
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    private final ShareLotService shareLotService;

    @PostMapping("/list")
    public List<ParkingListDto> getParkingList(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.getListOfPoint(parkingInDto);
    }

    //바텀 리스트

    @PostMapping("/list/bottom")
    public List<ParkingBottomListDto> getParkingBottomList(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.
    }


}
