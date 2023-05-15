package com.team.parking.domain.repository

import com.team.parking.data.model.favorite.FavoriteListResponse
import com.team.parking.data.util.Resource

interface FavoriteRepository {
    suspend fun setFavorite(parkId: Long, parkType: Int, userId: Long): Resource<Boolean>
    suspend fun getFavoriteList(userId: Long): Resource<List<FavoriteListResponse>>
}