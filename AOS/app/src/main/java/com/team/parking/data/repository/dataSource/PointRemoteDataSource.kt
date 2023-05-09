package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.point.ChargePointResponse
import com.team.parking.data.model.point.EarnedPointResponse
import com.team.parking.data.model.point.SpentPointResponse
import retrofit2.Response

interface PointRemoteDataSource {
    suspend fun getCurrentPoint(userId: Long) : Response<Int>
    suspend fun putChargePoint(userId: Long, ptCharge: Int) : Response<ChargePointResponse>
    suspend fun getEarnedPoint(
        userId: Long,
        year: String,
        month: String
    ) : Response<List<EarnedPointResponse>>
    suspend fun getSpentPoint(
        userId: Long,
        year: String,
        month: String
    ) : Response<List<SpentPointResponse>>
}