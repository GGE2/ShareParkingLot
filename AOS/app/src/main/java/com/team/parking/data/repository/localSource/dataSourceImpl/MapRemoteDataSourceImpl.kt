package com.team.parking.data.repository.localSource.dataSourceImpl

import com.team.parking.data.api.MapAPIService
import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.map.MapOrderResponse
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.repository.dataSource.MapRemoteDataSource
import retrofit2.Response

class MapRemoteDataSourceImpl(
    private val mapAPIService: MapAPIService
) : MapRemoteDataSource{
    override suspend fun getParkingLots(mapRequest: MapRequest): Response<List<MapResponse>> {
        return mapAPIService.getMapsData(mapRequest)
    }

    override suspend fun getParkingLotDetail(parkId: Int, userId: Long): Response<MapDetailResponse> {
        return mapAPIService.getMapDetailData(parkId, userId)
    }

    override suspend fun getParkingLotOrderByDistance(mapRequest: MapRequest): Response<List<MapOrderResponse>> {
        return mapAPIService.getParkingOrderByDistance(mapRequest)
    }

    override suspend fun getParkingLotOrderByPrice(mapRequest: MapRequest): Response<List<MapOrderResponse>> {
        return mapAPIService.getParkingOrderByPrice(mapRequest)
    }


}