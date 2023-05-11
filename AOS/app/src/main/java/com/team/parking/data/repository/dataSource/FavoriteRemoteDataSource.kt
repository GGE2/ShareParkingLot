package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.favorite.FavoriteListResponse
import retrofit2.Response

interface FavoriteRemoteDataSource {
    suspend fun setFavorite(parkId: Long, parkType: Int, userId: Long): Response<Boolean>
    suspend fun getFavoriteList(userId: Long): Response<List<FavoriteListResponse>>
}