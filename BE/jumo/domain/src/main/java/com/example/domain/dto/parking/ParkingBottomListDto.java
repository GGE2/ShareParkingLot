package com.example.domain.dto.parking;


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
    private float parkId;
    private float lat;
    private float lng;
    private int parkingField;
    private String parkingName;
    private String payType;
    private int feeBasic;

    private int meter;

    private String jibun;


    @Builder
    public ParkingBottomListDto(ShareLot shareLot){
        this.parkId = shareLot.getShaId();
        this.lat = shareLot.getLatitude();
        this.lng = shareLot.getLongitude();
        this.parkingField = shareLot.getSha_field();
        this.parkingName = shareLot.getSha_name();
        this.payType = "포인트결제";
        this.feeBasic = shareLot.getShaFee();
        this.meter = 0;
        this.jibun = shareLot.getSha_jibun();
    }

    @Builder
    public ParkingBottomListDto(ParkingLot parkingLot){
        this.parkId = parkingLot.getLotId();
        this.lat = parkingLot.getLatitude();
        this.lng = parkingLot.getLongitude();
        this.parkingField = parkingLot.getLot_field();
        this.parkingName = parkingLot.getLot_name();
        this.payType = parkingLot.getLot_part() + " "+ parkingLot.getPay_type();
        this.feeBasic = parkingLot.getPerBasic() * 2;
        this.meter = 0;
        this.jibun = parkingLot.getOld_addr();
    }

    @Builder
    public ParkingBottomListDto(ParkingLot parkingLot, int meter){
        this.parkId = parkingLot.getLotId();
        this.lat = parkingLot.getLatitude();
        this.lng = parkingLot.getLongitude();
        this.parkingField = parkingLot.getLot_field();
        this.parkingName = parkingLot.getLot_name();
        this.payType = parkingLot.getLot_part() + " "+ parkingLot.getPay_type();
        this.feeBasic = parkingLot.getPerBasic() * 2;
        this.meter = meter;
        this.jibun = parkingLot.getOld_addr();
    }

    @Builder
    public ParkingBottomListDto(ShareLot shareLot, int meter){
        this.parkId = shareLot.getShaId();
        this.lat = shareLot.getLatitude();
        this.lng = shareLot.getLongitude();
        this.parkingField = shareLot.getSha_field();
        this.parkingName = shareLot.getSha_name();
        this.payType = "포인트결제";
        this.feeBasic = shareLot.getShaFee();
        this.meter = meter;
        this.jibun = shareLot.getSha_jibun();
    }

}
