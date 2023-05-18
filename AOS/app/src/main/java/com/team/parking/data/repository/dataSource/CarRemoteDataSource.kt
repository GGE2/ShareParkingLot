package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.car.CarListResponse
import com.team.parking.data.model.car.CarSaveDto
import retrofit2.Response

interface CarRemoteDataSource {
    suspend fun setRepCar(carId: Long, userId: Long) : Response<Boolean>
    suspend fun getCarList(userId: Long) : Response<List<CarListResponse>>
    suspend fun postCar(carSaveDto: CarSaveDto, userId: Long) : Response<Unit>
}