package com.team.parking.domain.usecase

import com.team.parking.data.model.user.UpdateFcmTokenRequest
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository
import retrofit2.Response

class PutFcmTokenUseCase (
    private val userRepository : UserRepository
){
    suspend fun execute(updateFcmTokenRequest: UpdateFcmTokenRequest): Resource<Boolean> {
        return userRepository.updateFcmToken(updateFcmTokenRequest)
    }
}