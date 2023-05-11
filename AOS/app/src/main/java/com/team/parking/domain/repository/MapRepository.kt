package com.team.parking.domain.repository

import com.team.parking.data.model.map.*
import com.team.parking.data.util.Resource
import retrofit2.Response

interface MapRepository {

    suspend fun getParkingLots(mapRequest: MapRequest) : Resource<List<MapResponse>>
    suspend fun getParkingLotDetail(parkId:Int, userId: Long) : Resource<MapDetailResponse>
    suspend fun getParkingLotOrderByDistance(mapRequest: MapRequest) : Resource<List<MapOrderResponse>>
    suspend fun getParkingLotOrderByPrice(mapRequest: MapRequest) : Resource<List<MapOrderResponse>>
    
}