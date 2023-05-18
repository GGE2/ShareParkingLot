package com.team.parking.data.repository

import com.team.parking.data.model.favorite.FavoriteListResponse
import com.team.parking.data.model.map.MapOrderResponse
import com.team.parking.data.repository.dataSource.FavoriteRemoteDataSource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.FavoriteRepository
import retrofit2.Response

class FavoriteRepositoryImpl(
    private val favoriteRemoteDataSource: FavoriteRemoteDataSource
):FavoriteRepository {
    override suspend fun setFavorite(parkId: Long, parkType: Int, userId: Long): Resource<Boolean> {
        return responseToBoolean(favoriteRemoteDataSource.setFavorite(parkId, parkType, userId))
    }

    override suspend fun getFavoriteList(userId: Long): Resource<List<FavoriteListResponse>> {
        return responseToListFavoriteListResponse(favoriteRemoteDataSource.getFavoriteList(userId))
    }

    private fun responseToBoolean(response: Response<Boolean>): Resource<Boolean> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToListFavoriteListResponse(response:Response<List<FavoriteListResponse>>): Resource<List<FavoriteListResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}