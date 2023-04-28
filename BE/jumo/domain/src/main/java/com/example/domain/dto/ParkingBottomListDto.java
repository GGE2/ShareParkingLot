package com.example.domain.dto;


import com.example.domain.entity.ParkingLot;
import com.example.domain.entity.ShareLot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingBottomListDto {

    private float lat;
    private float lng;
    private int parkingField;
    private String parkingName;
    private String payType;
    private int feeBasic;

    @Builder
    public ParkingBottomListDto(ParkingLot parkingLot){
        this.lat = parkingLot.getLatitude();
        this.lng = parkingLot.getLongitude();
        this.parkingField = 10;
        this.parkingName = parkingLot.getLot_name();
        this.payType = parkingLot.getLot_part() + parkingLot.getPay_type();
        this.feeBasic = parkingLot.getPer_basic() * 2;
    }

    @Builder
    public ParkingBottomListDto(ShareLot shareLot){
        this.lat = shareLot.getLatitude();
        this.lng = shareLot.getLongitude();
        this.parkingField = 10;
        this.parkingName = shareLot.getSha_name();
        this.payType = "포인트결제";
        this.feeBasic = shareLot.getSha_fee();
    }

}