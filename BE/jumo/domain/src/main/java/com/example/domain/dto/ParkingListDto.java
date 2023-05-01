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
public class ParkingListDto {
    private Long parkId;
    private float lat;
    private float lng;
    private int parkType;
    private int feeBasic;
    private int clusteringCnt;


    @Builder
    public ParkingListDto(ParkingLot parkingLot){
        this.parkId = parkingLot.getLotId();
        this.lat = parkingLot.getLatitude();
        this.lng = parkingLot.getLongitude();
        this.parkType = 0;
        this.feeBasic = parkingLot.getFee_data() == "무료" ? -1 : parkingLot.getPer_basic() * 2;
        this.clusteringCnt = 0;
    }


    @Builder
    public ParkingListDto(ShareLot shareLot){
        this.parkId = shareLot.getShaId();
        this.lat = shareLot.getLatitude();
        this.lng = shareLot.getLongitude();
        this.parkType = 1;
        this.feeBasic = shareLot.getSha_fee();
        this.clusteringCnt = 0;
    }
}
