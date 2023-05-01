package com.example.domain.dto;


import com.example.domain.entity.CarInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarInfoDto {
    private Long car_id;
    private String carStr;
    private boolean carRep;

    @Builder
    public CarInfoDto(CarInfo carInfo){
        this.car_id = carInfo.getCar_id();
        this.carStr = carInfo.getCar_str();
        this.carRep = carInfo.getCar_rep();
    }
}
