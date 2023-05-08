package com.team.parking.data.repository

import com.team.parking.data.model.map.MapDetailResponse
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

    override suspend fun getParkingLotDetail(parkId: Int): Resource<MapDetailResponse> {
        return responseToMapDetailResource(mapRemoteDatasource.getParkingLotDetail(parkId))
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