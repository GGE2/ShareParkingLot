package com.team.parking.domain.usecase

import com.team.parking.data.model.car.CarSaveDto
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.CarRepository

class PostCarUseCase (
    private val carRepository: CarRepository
) {
    suspend fun execute(carSaveDto: CarSaveDto, userId: Long): Resource<Unit> {
        return carRepository.postCar(carSaveDto, userId)
    }
}