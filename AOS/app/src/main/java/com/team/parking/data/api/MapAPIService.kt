package com.team.parking.data.api

import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.model.map.SearchKeyWordResponse
import retrofit2.Response
import retrofit2.http.*

interface MapAPIService {

    @POST("parkingLot/list")
    suspend fun getMapsDataFrom(
        @Body mapRequest: MapRequest
    ) : Response<List<MapResponse>>

}