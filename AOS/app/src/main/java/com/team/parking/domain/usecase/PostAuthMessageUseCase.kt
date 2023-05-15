package com.team.parking.domain.usecase

import com.team.parking.data.model.user.PhoneRequest
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository
import retrofit2.Response

class PostAuthMessageUseCase (
    private val userRepository: UserRepository
        ){
    suspend fun execute(phone: PhoneRequest): Resource<Boolean> {
        return userRepository.sendAuthMessage(phone)
    }
}