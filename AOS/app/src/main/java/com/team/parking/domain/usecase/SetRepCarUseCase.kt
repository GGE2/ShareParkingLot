package com.team.parking.domain.usecase

import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.CarRepository

class SetRepCarUseCase (
    private val carRepository: CarRepository
) {
    suspend fun execute(carId: Long, userId: Long): Resource<Boolean> {
        return carRepository.setRepCar(carId, userId)
    }
}