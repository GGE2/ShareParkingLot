package com.team.parking.domain.usecase

import com.team.parking.data.model.point.SpentPointResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.PointRepository

class GetSpentPointUseCase (
    private val pointRepository: PointRepository
) {
    suspend fun execute(
        userId: Long,
        year: String,
        month: String
    ): Resource<List<SpentPointResponse>> {
        return pointRepository.getSpentPoint(userId, year, month)
    }
}