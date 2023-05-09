package com.team.parking.data.api

import com.team.parking.data.model.car.CarListResponse
import com.team.parking.data.model.car.CarSaveDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CarAPIService {
    @GET("carInfo/checkRep")
    suspend fun setRepCar(@Query("carId") carId: Long, @Query("userId") userId: Long) : Response<Boolean>

    @GET("carInfo/list")
    suspend fun getCarList(@Query("userId") userId: Long) : Response<List<CarListResponse>>

    @POST("carInfo/save")
    suspend fun postCar(@Body carSaveDto: CarSaveDto, @Query("userId") userId: Long) : Response<Unit>
}