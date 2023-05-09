package com.team.parking.data.api

import com.team.parking.data.model.point.ChargePointResponse
import com.team.parking.data.model.point.EarnedPointResponse
import com.team.parking.data.model.point.SpentPointResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface PointAPIService {
    @GET("point/{userId}")
    suspend fun getCurrentPoint(@Path("userId") userId: Long) : Response<Int>

    @PUT("point/charge/{userId}/{ptCharge}")
    suspend fun putChargePoint(@Path("userId") userId: Long, @Path("ptCharge") ptCharge: Int) : Response<ChargePointResponse>

    @GET("point/earned/{userId}/{year}/{month}")
    suspend fun getEarnedPoint(
        @Path("userId") userId: Long,
        @Path("year") year: String,
        @Path("month") month: String
    ) : Response<List<EarnedPointResponse>>

    @GET("point/spent/{userId}/{year}/{month}")
    suspend fun getSpentPoint(
        @Path("userId") userId: Long,
        @Path("year") year: String,
        @Path("month") month: String
    ) : Response<List<SpentPointResponse>>
}