package com.example.jumoparking.controller;

import com.example.domain.dto.CarInfoDto;
import com.example.domain.dto.CarSaveDto;
import com.example.jumoparking.service.CarInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carInfo")
@RequiredArgsConstructor
@Api(tags = "차량정보")
public class CarInfoController {

    private final CarInfoService carInfoService;

    @ApiOperation(value = "차량 등록", notes = "차량 번호만 담아서 보내주면 될 듯 , 대표차량 한 대 등록할 때 자동 설정하고 싶으면 로직 수정해야함 말 좀")
    @PostMapping("/save")
    public void saveCar(@RequestBody CarSaveDto carSaveDto, @RequestParam Long userId){
        carInfoService.saveCarInfo(carSaveDto, userId);
    }

    @ApiOperation(value = "차량리스트", notes = "차량 아이디, 번호, 대표차인지 여부")
    @GetMapping("/list")
    public List<CarInfoDto> listCar(@RequestParam Long userId){
        return carInfoService.getListCarInfo(userId);
    }

    @ApiOperation(value = "대표차량 설정", notes = "대표차량 하나 설정하면 자동으로 다른 차량은 취소 됨")
    @GetMapping("/checkRep")
    public boolean checkRep(@RequestParam Long carId, @RequestParam Long userId){
        return carInfoService.checkRep(carId, userId);
    }

}
