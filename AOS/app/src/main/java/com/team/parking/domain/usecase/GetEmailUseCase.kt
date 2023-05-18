package com.team.parking.domain.usecase

import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository

class GetEmailUseCase (
    private val userRepository: UserRepository
        ){
    suspend fun execute(email: String): Resource<Boolean> {
        return userRepository.checkEmail(email)
    }
}