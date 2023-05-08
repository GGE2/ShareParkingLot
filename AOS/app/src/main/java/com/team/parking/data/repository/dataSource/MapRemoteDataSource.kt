package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import retrofit2.Response

interface MapRemoteDataSource {

    suspend fun getParkingLots(mapRequest: MapRequest) : Response<List<MapResponse>>
    suspend fun getParkingLotDetail(parkId : Int) : Response<MapDetailResponse>
}