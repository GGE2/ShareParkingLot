package com.team.parking.data.api

import com.team.parking.data.model.favorite.FavoriteListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FavoriteAPIService {
    @GET("parkingLot/favorite")
    suspend fun setFavorite(@Query("parkId") parkId: Long, @Query("parkType") parkType: Int, @Query("userId") userId: Long): Response<Boolean>

    @GET("parkingLot/favorite/list")
    suspend fun getFavoriteList(@Query("userId") userId: Long): Response<List<FavoriteListResponse>>
}