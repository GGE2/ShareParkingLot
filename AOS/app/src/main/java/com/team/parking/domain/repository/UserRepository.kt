package com.team.parking.domain.repository

import com.team.parking.data.model.user.*
import com.team.parking.data.util.Resource
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Part
import retrofit2.http.Query

interface UserRepository {
    suspend fun login(
        type: String,
        accessToken: String?,
        email: String?,
        password: String?,
        social_id: String?,
        name: String?,
        profile_img: String?,
        fcm_token: String?
    ): Resource<LoginResponse>

    suspend fun checkEmail(email: String): Resource<Boolean>

    suspend fun signUp(request: SignUpRequest): Resource<User>

    suspend fun updateFcmToken(updateFcmTokenRequest: UpdateFcmTokenRequest): Resource<Boolean>

    suspend fun sendAuthMessage(phone: PhoneRequest): Resource<Boolean>

    suspend fun confirmPhone(phone: String, code: String): Resource<Boolean>

    suspend fun getUserInfo(user_id : Long) : Resource<UserProfileResponse>

    suspend fun updateProfileImg(file: MultipartBody.Part, user_id : Long) : Resource<UpdateProfileImageResponse>
}