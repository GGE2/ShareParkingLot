package com.example.jumoparking.service.impl;

import com.example.domain.dto.CarInfoDto;
import com.example.domain.dto.CarSaveDto;
import com.example.domain.entity.CarInfo;
import com.example.domain.repo.CarInfoRepo;
import com.example.domain.repo.UserRepo;
import com.example.jumoparking.service.CarInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarInfoServiceImpl implements CarInfoService {

    private final CarInfoRepo carInfoRepo;
    private final UserRepo userRepo;
    @Override
    public List<CarInfoDto> getListCarInfo(Long userId) {
        return carInfoRepo.findCarInfosByUser_UserId(userId).stream().map(carInfo -> new CarInfoDto(carInfo)).collect(Collectors.toList());
    }

    @Override
    public void saveCarInfo(CarSaveDto carSaveDto, Long userId) {
        CarInfo carInfo = CarInfo.builder()
                .car_str(carSaveDto.getCarStr())
                .user(userRepo.findById(userId).get())
                .build();
        carInfoRepo.save(carInfo);
    }

    @Override
    public boolean checkRep(Long carId, Long userId) {
        List<CarInfo> carInfos = carInfoRepo.findCarInfosByUser_UserId(userId);
        for(CarInfo carInfo : carInfos){
            if(carInfo.getCar_rep()){
                carInfo.setCar_rep(false);
            }
            if(carInfo.getCar_id() == carId){
                carInfo.setCar_rep(true);
            }
        }
        return true;
    }
}
