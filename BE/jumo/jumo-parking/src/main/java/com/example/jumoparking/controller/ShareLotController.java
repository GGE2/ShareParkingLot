package com.example.jumoparking.controller;

import com.example.domain.dto.DaySaveDto;
import com.example.domain.dto.MyShareListDto;
import com.example.domain.dto.ParkingDetailDto;
import com.example.domain.dto.ShareSaveDto;
import com.example.domain.entity.ShareLot;
import com.example.jumoparking.service.DayDataService;
import com.example.jumoparking.service.ShareLotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shareLot")
@RequiredArgsConstructor
@Api(tags = "공유 주차장")
public class ShareLotController {

    private final ShareLotService shareLotService;
    private final DayDataService dayDataService;
    @ApiOperation(value = "공유 주차장 등록", notes = "멀티파트파일 리스트를 보내주어야 함 이게 좀 어려울거같으면 같이 방법 찾아보기")
    @PostMapping(value =  "/save",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long createShareLot(@Validated ShareSaveDto saveDto, @RequestParam Long userId, @RequestPart(required = false) List<MultipartFile> files) throws  Exception{
        return shareLotService.saveShareLot(saveDto, userId,files);
    }

    @ApiOperation(value = "요일 정보 등록", notes = "프론트 로직을 다른 정보 등록 -> 요일정보 등록 페이지로 넘어가는 느낌으로 생각해서 분리함")
    @PostMapping("/saveDay")
    public boolean saveDay(@RequestParam Long parkId, @RequestBody List<DaySaveDto> daySaveDtos){
        for (DaySaveDto daySaveDto : daySaveDtos){
            dayDataService.saveDayData(daySaveDto, parkId);
        }
        return true;
    }

    @ApiOperation(value = "요일 수정", notes = "이건 내공유 주차장 페이지 가서 버튼 누를 때, 새롭게 월~일 리스트 새로 보내주기// 공유에 달려있는 기존 데이터 싹 사젝 후 다시 등록하는 ㄴ식")
    @PostMapping("/updateDay")
    public void updateDay(@RequestParam Long parkId, @RequestBody List<DaySaveDto> daySaveDtos){
        dayDataService.deleteDayData(parkId);
        for(DaySaveDto daySaveDto : daySaveDtos){
            dayDataService.saveDayData(daySaveDto, parkId);
        }
    }

    @ApiOperation(value = "공유주차장 삭제", notes = "return 값 없음")
    @DeleteMapping("/delete")
    public void deleteShareLot(@RequestParam Long parkId){
        shareLotService.deleteShareLot(parkId);
    }


    @ApiOperation(value = "공유주차장 상세", notes = "일반 주차장과 리턴은 동일하지만, parkType 같은거 param 들어가는 거 싫어서 일단 분리함")
    @GetMapping("/detail")
    public ParkingDetailDto shareLotDetail(@RequestParam Long parkId){ return shareLotService.getDetail(parkId);}

    @ApiOperation(value = "공유주차장 개요 리스트", notes = "나의 공유주차장에서 표시해줄 간단한 데이터들 리스트")
    @GetMapping("/myList")
    public List<MyShareListDto> shareList(@RequestParam Long userId){
        return shareLotService.getListMyShare(userId);
    }

}
