package com.example.domain.dto;

import com.example.domain.entity.DayData;
import com.example.domain.entity.Image;
import com.example.domain.entity.ParkingLot;
import com.example.domain.entity.ShareLot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingDetailDto {
    private Long parkId;
    private String lotType;
    private String lotName;
    private String roadAddr;
    private String jibun;
    private String openDay;
    private String feeData;
    private int feeBasic;
    private int dayFee;
    private String payType;
    private String specialProp;
    private List<String> imageUrl;

    @Builder
    public ParkingDetailDto(ParkingLot parkingLot){
        this.parkId = parkingLot.getLotId();
        this.lotName = parkingLot.getLot_name();
        this.lotType = parkingLot.getLot_type();
        this.roadAddr = parkingLot.getRoad_addr();
        this.jibun = parkingLot.getOld_addr();
        this.openDay = parkingLot.getOpen_day();
        this.feeData = parkingLot.getFee_data();
        this.feeBasic = parkingLot.getPer_basic();
        this.dayFee = parkingLot.getPer_day();
        this.payType = parkingLot.getPay_type();
        this.specialProp = parkingLot.getSpecial_prop();
        this.imageUrl = new ArrayList<>();
    }

    @Builder
    public ParkingDetailDto(ShareLot shareLot){
        this.parkId = shareLot.getShaId();
        this.lotName = shareLot.getSha_name();
        this.lotType = "공유";
        this.roadAddr = shareLot.getSha_road();
        this.jibun = shareLot.getSha_jibun();
        List<DayData> dayList = shareLot.getDayDataList();
        String openDayString = "";
        for (DayData dayData : dayList){
            openDayString += dayData.getDay_str().toString() + " ";
        }
        this.openDay = openDayString;
        this.feeData = "유료";
        this.feeBasic = shareLot.getSha_fee();
        this.dayFee = shareLot.getSha_fee() * 48;
        this.payType = "포인트결제";
        this.specialProp = shareLot.getSha_prop();
        List<Image> images = shareLot.getImages();
        List<String> imageUrl = new ArrayList<>();
        for (Image image :images){
            imageUrl.add(image.getUrl());
        }
        this.imageUrl = imageUrl;
    }
}
