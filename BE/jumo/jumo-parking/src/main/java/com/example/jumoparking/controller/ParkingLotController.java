package com.example.jumoparking.controller;

import com.example.domain.dto.parking.ParkingBottomListDto;
import com.example.domain.dto.parking.ParkingDetailDto;
import com.example.domain.dto.parking.ParkingInDto;
import com.example.domain.dto.parking.ParkingListDto;
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
    public ParkingDetailDto shareLotDetail(@RequestParam Long parkId){ return parkingLotService.getDetail(parkId);}

    @ApiOperation(value = "즐겨찾기", notes = "필요한 param 이 좀 많음, parkType 은 공유주차장인지 일반주차장인지 구분")
    @GetMapping("/favorite")
    public boolean checkFavorite(@RequestParam Long parkId, @RequestParam Long userId, @RequestParam int parkType){
        if(parkType == 1){
            return shareLotService.checkFavorite(userId, parkId);
        }
        else{
            return parkingLotService.checkFavorite(userId, parkId);
        }
    }

    @ApiOperation(value = "즐겨찾기한 주차장 목록", notes = "바텀 목록이랑 받는 데이터는 같음.")
    @GetMapping("/favorite/list")
    public List<ParkingBottomListDto> getFavoriteList(@RequestParam Long userId){
        return parkingLotService.getFavoriteList(userId);
    }

    @ApiOperation(value = "일반주차장만 보기", notes = "공유 주차장만 보기는 sharLot/* 쪽에 있음")
    @PostMapping("/list/parking")
    public List<ParkingListDto> getListOfParking(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.getListOfParking(parkingInDto);
    }
    @ApiOperation(value = "목록보기 거리순", notes = "목록보기를 할 때 기본적으로 dto 에 meter 추가")
    @PostMapping("/list/bottom/dist")
    public List<ParkingBottomListDto> getBottomListOfDist(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.getOrderByDist(parkingInDto);
    }

    @ApiOperation(value = "목록보기 가격순", notes = "meter 정보도 같이 표시 된다.")
    @PostMapping("/list/bottom/price")
    public List<ParkingBottomListDto> getBottomListOfPrice(@Validated @RequestBody ParkingInDto parkingInDto){
        return parkingLotService.getOrderByPrice(parkingInDto);
    }


}
