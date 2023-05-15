package com.team.parking.domain.usecase

import com.team.parking.data.model.point.ChargePointResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.PointRepository

class PutChargePointUseCase (
    private val pointRepository: PointRepository
        ) {
    suspend fun execute(userId: Long, ptCharge: Int) : Resource<ChargePointResponse> {
        return pointRepository.putChargePoint(userId, ptCharge)
    }
}