package com.team.parking.data.repository.dataSourceImpl

import com.team.parking.data.api.CarAPIService
import com.team.parking.data.model.car.CarListResponse
import com.team.parking.data.model.car.CarSaveDto
import com.team.parking.data.repository.dataSource.CarRemoteDataSource
import retrofit2.Response

class CarRemoteDataSourceImpl(
    private val carAPIService: CarAPIService
) : CarRemoteDataSource {
    override suspend fun setRepCar(carId: Long, userId: Long): Response<Boolean> {
        return carAPIService.setRepCar(carId, userId)
    }

    override suspend fun getCarList(userId: Long): Response<List<CarListResponse>> {
        return carAPIService.getCarList(userId)
    }

    override suspend fun postCar(carSaveDto: CarSaveDto, userId: Long): Response<Unit> {
        return carAPIService.postCar(carSaveDto, userId)
    }
}