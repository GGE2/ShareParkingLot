package com.example.domain.dto;


import com.example.domain.entity.ParkingLot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingListDto {
    private float lat;
    private float lng;
    private int parkType;
    private int feeBasic;

    private int clusteringCnt;

    @Builder
    public ParkingListDto(ParkingLot parkingLot){
        this.lat = parkingLot.getLatitude();
        this.lng = parkingLot.getLongitude();
        this.parkType = 0;
        this.feeBasic = parkingLot.getPer_basic() * 6;
        this.clusteringCnt = 0;
    }
}
