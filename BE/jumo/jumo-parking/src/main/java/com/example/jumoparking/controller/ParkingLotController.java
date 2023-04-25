package com.example.jumoparking.controller;

import com.example.jumoparking.dto.ParkingInDto;
import com.example.jumoparking.dto.ParkingListDto;
import com.example.jumoparking.service.ParkingLotService;
import com.example.jumoparking.service.impl.ParkingLotServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingLot")
@RequiredArgsConstructor
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    @PostMapping("/list")
    public List<ParkingListDto> getParkingList(@Validated @RequestBody ParkingInDto parkingInDto){
        System.out.println('1');
        return parkingLotService.getListOfPoint(parkingInDto);
    }


}
