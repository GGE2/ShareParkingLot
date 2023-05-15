package com.team.parking.domain.usecase

import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository
import retrofit2.Response

class GetAuthMessageUseCase (
    private val userRepository: UserRepository
        ){
    suspend fun execute(phone: String, code: String): Resource<Boolean> {
        return userRepository.confirmPhone(phone,code)
    }
}