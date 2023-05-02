package com.team.parking.data.repository

import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.repository.dataSource.MapRemoteDatasource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.MapRepository
import retrofit2.Response

class MapRepositoryImpl(
    private val mapRemoteDatasource: MapRemoteDatasource
) : MapRepository{
    override suspend fun getParkingLots(
        mapRequest: MapRequest
    ): Resource<List<MapResponse>> {
        return responseToResource(mapRemoteDatasource.getParkingLots(mapRequest))
    }

    private fun responseToResource(response:Response<List<MapResponse>>): Resource<List<MapResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}