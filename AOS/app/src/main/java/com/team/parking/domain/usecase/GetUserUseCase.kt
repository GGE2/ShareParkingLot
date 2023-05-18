package com.team.parking.domain.usecase

import com.team.parking.data.model.user.*
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository
import retrofit2.Response

class GetUserUseCase (
        private val userRepository: UserRepository
        ){
    suspend fun execute(type: String,
                        accessToken: String?,
                        email: String?,
                        password: String?,
                        social_id: String?,
                        name: String?,
                        profile_img: String?,
                        fcm_token: String?) : Resource<LoginResponse> {
        return userRepository.login(type, accessToken, email, password, social_id, name, profile_img, fcm_token)
    }
}










