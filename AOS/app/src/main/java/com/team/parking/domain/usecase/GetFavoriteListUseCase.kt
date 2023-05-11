package com.team.parking.domain.usecase

import com.team.parking.data.model.favorite.FavoriteListResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.FavoriteRepository

class GetFavoriteListUseCase (
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun execute(userId: Long) : Resource<List<FavoriteListResponse>> {
        return favoriteRepository.getFavoriteList(userId)
    }
}