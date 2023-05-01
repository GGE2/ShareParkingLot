package com.example.jumoparking.controller;

import com.example.domain.dto.ParkingBottomListDto;
import com.example.domain.dto.ParkingDetailDto;
import com.example.domain.dto.ParkingInDto;
import com.example.domain.dto.ParkingListDto;
import com.example.jumoparking.service.ParkingLotService;
import com.example.jumoparking.service.ShareLotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingLot")
@RequiredArgsConstructor
@Api(tags = "일반 주차장 및 일반+공유 혼합")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    private final ShareLotService shareLotService;

    @ApiOperation(value = "지도를 위한 주차장 리스트", notes = "공유 주차장 타입 1, 일반 주차장 타입 0")
    @PostMapping("/list")
    public List<ParkingListDto> getParkingList(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.getListOfPoint(parkingInDto);
    }

    //바텀 리스트

    @ApiOperation(value = "바텀 목록 리스트", notes = "detail로 갈 수 있게 Id 포함, 마찬가지로 공유 일반 포함")
    @PostMapping("/list/bottom")
    public List<ParkingBottomListDto> getParkingBottomList(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.getBottomListOfPoint(parkingInDto);
    }
    @ApiOperation(value = "주차장 상세", notes = "임의로 공유랑 일반 주차장 상세 통일함, 혹시 분리하고 싶거나 더 필요한 데이터 있으면 말해줘 이미지 url 또한 포함 됨")
    @GetMapping("/detail")
    public ParkingDetailDto shareLotDetail(@RequestParam Long lotId){ return parkingLotService.getDetail(lotId);}

    @ApiOperation(value = "즐겨찾기", notes = "필요한 param 이 좀 많음, parkType 은 공유주차장인지 일반주차장인지 구분")
    @GetMapping("/favorite")
    public boolean checkFavorite(@RequestParam Long lotId, @RequestParam Long userId, @RequestParam int parkType){
        if(parkType == 1){
            return shareLotService.checkFavorite(userId, lotId);
        }
        else{
            return parkingLotService.checkFavorite(userId, lotId);
        }
    }

    @ApiOperation(value = "즐겨찾기한 주차장 목록", notes = "바텀 목록이랑 받는 데이터는 같음.")
    @GetMapping("/favorite/list")
    public List<ParkingBottomListDto> getFavoriteList(@RequestParam Long userId){
        return parkingLotService.getFavoriteList(userId);
    }



}
