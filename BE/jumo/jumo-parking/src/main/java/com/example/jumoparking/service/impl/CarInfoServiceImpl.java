package com.example.jumoparking.service.impl;

import com.example.domain.dto.parking.CarInfoDto;
import com.example.domain.dto.parking.CarSaveDto;
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
        List<CarInfoDto> carInfoList = carInfoRepo.findCarInfosByUser_UserId(userId).stream().map(carInfo -> new CarInfoDto(carInfo)).collect(Collectors.toList());
        for(int i =0 ; i < carInfoList.size(); i++){
            if(carInfoList.get(i).isCarRep()){
                if(i != 0){
                    CarInfoDto tmpCarInfoDto = carInfoList.get(0);
                    carInfoList.set(0, carInfoList.get(i));
                    carInfoList.set(i, tmpCarInfoDto);
                    break;
                }else{
                    break;
                }
            }
        }
        return carInfoList;
    }

    @Override
    public void saveCarInfo(CarSaveDto carSaveDto, Long userId) {
        if (carInfoRepo.findCarInfosByUser_UserId(userId).size() == 0){
            CarInfo carInfo = CarInfo.builder()
                    .car_str(carSaveDto.getCarStr())
                    .car_rep(true)
                    .user(userRepo.findById(userId).get())
                    .build();
            carInfoRepo.save(carInfo);
        }
        else {
            CarInfo carInfo = CarInfo.builder()
                    .car_str(carSaveDto.getCarStr())
                    .user(userRepo.findById(userId).get())
                    .build();
            carInfoRepo.save(carInfo);
        }
    }

    @Override
    public boolean checkRep(Long carId, Long userId) {
        List<CarInfo> carInfos = carInfoRepo.findCarInfosByUser_UserId(userId);
        for(CarInfo carInfo : carInfos){
            if(carInfo.getCar_rep()){
                carInfo.setCar_rep(false);
                carInfoRepo.save(carInfo);
            }
            if(carInfo.getCar_id() == carId){
                carInfo.setCar_rep(true);
                carInfoRepo.save(carInfo);
            }
        }
        return true;
    }
}
