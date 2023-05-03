package com.team.parking.domain.repository

import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.util.Resource
import retrofit2.Response

interface MapRepository {

    suspend fun getParkingLots(mapRequest: MapRequest) : Resource<List<MapResponse>>
    suspend fun getParkingLotDetail(parkId:Int) : Resource<MapDetailResponse>
}