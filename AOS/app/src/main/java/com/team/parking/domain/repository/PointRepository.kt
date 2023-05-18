package com.team.parking.domain.repository

import com.team.parking.data.model.point.ChargePointResponse
import com.team.parking.data.model.point.EarnedPointResponse
import com.team.parking.data.model.point.SpentPointResponse
import com.team.parking.data.util.Resource

interface PointRepository {
    suspend fun getCurrentPoint(userId: Long) : Resource<Int>
    suspend fun putChargePoint(userId: Long, ptCharge: Int) : Resource<ChargePointResponse>
    suspend fun getEarnedPoint(
        userId: Long,
        year: String,
        month: String
    ) : Resource<List<EarnedPointResponse>>
    suspend fun getSpentPoint(
        userId: Long,
        year: String,
        month: String
    ) : Resource<List<SpentPointResponse>>
}