package com.team.parking.data.repository.dataSourceImpl

import com.team.parking.data.api.FavoriteAPIService
import com.team.parking.data.model.favorite.FavoriteListResponse
import com.team.parking.data.repository.dataSource.FavoriteRemoteDataSource
import retrofit2.Response

class FavoriteRemoteDataSourceImpl(
    private val favoriteAPIService: FavoriteAPIService
):FavoriteRemoteDataSource {
    override suspend fun setFavorite(parkId: Long, parkType: Int, userId: Long): Response<Boolean> {
        return favoriteAPIService.setFavorite(parkId, parkType, userId)
    }

    override suspend fun getFavoriteList(userId: Long): Response<List<FavoriteListResponse>> {
        return favoriteAPIService.getFavoriteList(userId)
    }
}