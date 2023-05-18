package com.team.parking.data.repository.dataSourceImpl

import com.team.parking.data.api.PointAPIService
import com.team.parking.data.model.point.ChargePointResponse
import com.team.parking.data.model.point.EarnedPointResponse
import com.team.parking.data.model.point.SpentPointResponse
import com.team.parking.data.repository.dataSource.PointRemoteDataSource
import retrofit2.Response

class PointRemoteDataSourceImpl(
    private val pointAPIService: PointAPIService
) :PointRemoteDataSource {
    override suspend fun getCurrentPoint(userId: Long): Response<Int> {
        return pointAPIService.getCurrentPoint(userId)
    }

    override suspend fun putChargePoint(
        userId: Long,
        ptCharge: Int
    ): Response<ChargePointResponse> {
        return pointAPIService.putChargePoint(userId, ptCharge)
    }

    override suspend fun getEarnedPoint(
        userId: Long,
        year: String,
        month: String
    ): Response<List<EarnedPointResponse>> {
        return pointAPIService.getEarnedPoint(userId, year, month)
    }

    override suspend fun getSpentPoint(
        userId: Long,
        year: String,
        month: String
    ): Response<List<SpentPointResponse>> {
        return pointAPIService.getSpentPoint(userId, year, month)
    }

}