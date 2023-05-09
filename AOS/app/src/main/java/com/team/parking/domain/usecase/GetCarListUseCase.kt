package com.team.parking.domain.usecase

import com.team.parking.data.model.car.CarListResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.CarRepository

class GetCarListUseCase (
    private val carRepository: CarRepository
) {
    suspend fun execute(userId: Long): Resource<List<CarListResponse>> {
        return carRepository.getCarList(userId)
    }
}