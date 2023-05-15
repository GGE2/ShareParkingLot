package com.team.parking.domain.usecase

import com.team.parking.data.model.user.UpdateProfileImageResponse
import com.team.parking.data.model.user.User
import com.team.parking.data.model.user.UserProfileResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository
import okhttp3.MultipartBody

class PutProfileImageUseCase (
    private val userRepository: UserRepository
){
    suspend fun execute(file: MultipartBody.Part, user_id : Long) : Resource<UpdateProfileImageResponse> {
        return userRepository.updateProfileImg(file,user_id)
    }
}