package com.team.parking.data.repository.dataSourceImpl

import com.team.parking.data.api.MapAPIService
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.repository.dataSource.MapRemoteDatasource
import retrofit2.Response

class MapRemoteDataSourceImpl(
    private val mapAPIService: MapAPIService
) : MapRemoteDatasource{
    override suspend fun getParkingLots(mapRequest: MapRequest): Response<List<MapResponse>> {
        return mapAPIService.getMapsDataFrom(mapRequest)
    }


}