package com.team.parking.data.repository

import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.map.MapOrderResponse
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.repository.dataSource.MapRemoteDataSource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.MapRepository
import retrofit2.Response

class MapRepositoryImpl(
    private val mapRemoteDatasource: MapRemoteDataSource
) : MapRepository{
    override suspend fun getParkingLots(
        mapRequest: MapRequest
    ): Resource<List<MapResponse>> {
        return responseToMapResource(mapRemoteDatasource.getParkingLots(mapRequest))
    }

    override suspend fun getParkingLotDetail(parkId: Int, userId: Long): Resource<MapDetailResponse> {
        return responseToMapDetailResource(mapRemoteDatasource.getParkingLotDetail(parkId, userId))
    }

    override suspend fun getParkingLotOrderByDistance(mapRequest: MapRequest): Resource<List<MapOrderResponse>> {
        return responseToMapOrderResource(mapRemoteDatasource.getParkingLotOrderByDistance(mapRequest))
    }

    override suspend fun getParkingLotOrderByPrice(mapRequest: MapRequest): Resource<List<MapOrderResponse>> {
        return responseToMapOrderResource(mapRemoteDatasource.getParkingLotOrderByPrice(mapRequest))
    }

    private fun responseToMapOrderResource(response:Response<List<MapOrderResponse>>): Resource<List<MapOrderResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }


    private fun responseToMapDetailResource(response:Response<MapDetailResponse>): Resource<MapDetailResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToMapResource(response:Response<List<MapResponse>>): Resource<List<MapResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}