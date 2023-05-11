package com.team.parking.domain.usecase

import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.FavoriteRepository

class SetFavoriteUseCase (
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun execute(parkId: Long, parkType: Int, userId: Long): Resource<Boolean> {
        return favoriteRepository.setFavorite(parkId, parkType, userId)
    }
}