package com.team.parking.domain.repository

import com.team.parking.data.model.car.CarListResponse
import com.team.parking.data.model.car.CarSaveDto
import com.team.parking.data.util.Resource

interface CarRepository {
    suspend fun setRepCar(carId: Long, userId: Long) : Resource<Boolean>
    suspend fun getCarList(userId: Long) : Resource<List<CarListResponse>>
    suspend fun postCar(carSaveDto: CarSaveDto, userId: Long) : Resource<Unit>
}