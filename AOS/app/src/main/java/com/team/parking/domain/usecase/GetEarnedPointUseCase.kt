package com.team.parking.domain.usecase

import com.team.parking.data.model.point.EarnedPointResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.PointRepository

class GetEarnedPointUseCase(
    private val pointRepository: PointRepository
) {
    suspend fun execute(
        userId: Long,
        year: String,
        month: String
    ): Resource<List<EarnedPointResponse>> {
        return pointRepository.getEarnedPoint(userId, year, month)
    }
}