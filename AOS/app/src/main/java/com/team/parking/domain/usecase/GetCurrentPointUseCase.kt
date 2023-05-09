package com.team.parking.domain.usecase

import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.PointRepository

class GetCurrentPointUseCase (
    private val pointRepository: PointRepository
        ) {
    suspend fun execute(userId: Long) : Resource<Int> {
        return pointRepository.getCurrentPoint(userId)
    }
}