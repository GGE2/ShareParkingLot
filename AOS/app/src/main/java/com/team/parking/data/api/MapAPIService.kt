package com.team.parking.data.api

import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import retrofit2.Response
import retrofit2.http.*

interface MapAPIService {

    @POST("parkingLot/list")
    suspend fun getMapsData(
        @Body mapRequest: MapRequest
    ) : Response<List<MapResponse>>

    @GET("parkingLot/detail")
    suspend fun getMapDetailData(
        @Query("parkId") parkId : Int
    ) : Response<MapDetailResponse>
}