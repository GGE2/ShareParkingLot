package com.team.parking.data.api

import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.model.parkinglot.ShareLotResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ShareLotAPIService {
    @DELETE("shareLot/delete")
    suspend fun deleteShareLot(@Query("parkId") parkId: Long) : Response<Unit>

    @GET("shareLot/detail")
    suspend fun getShareLotDetail(@Query("parkId") parkId: Long, @Query("userId") userId: Long) : Response<MapDetailResponse>

    @GET("shareLot/listDay")
    suspend fun getShareLotDay(@Query("parkId") parkId: Long) : Response<List<DayRequest>>

    @GET("shareLot/myList")
    suspend fun getShareLotList(@Query("userId") userId: Long) : Response<List<ShareLotResponse>>

    @Multipart
    @POST("shareLot/save")
    suspend fun postShareLot(
        @Part("saveDto") saveDto: ShareLotRequest,
        @Part files: List<MultipartBody.Part>
    ) : Response<Long>

    @PUT("shareLot/saveDay")
    suspend fun putSaveDay(
        @Body daySaveDtos: List<DayRequest>,
        @Query("parkId") parkId: Long
    ) : Response<Unit>
}