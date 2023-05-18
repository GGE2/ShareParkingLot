package com.team.parking.domain.usecase

import com.team.parking.data.model.user.SignUpRequest
import com.team.parking.data.model.user.User
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository

class PostUserUseCase(
    private val userRepository: UserRepository
){
    suspend fun execute(request: SignUpRequest): Resource<User>{
        return userRepository.signUp(request)
    }

}