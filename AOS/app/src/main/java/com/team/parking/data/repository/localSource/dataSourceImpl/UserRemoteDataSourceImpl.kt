package com.team.parking.data.repository.localSource.dataSourceImpl

import com.team.parking.data.api.UserAPIService
import com.team.parking.data.model.user.*
import com.team.parking.data.repository.dataSource.UserRemoteDataSource
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class UserRemoteDataSourceImpl (
    private val userAPIService : UserAPIService
) : UserRemoteDataSource {
    override suspend fun login(
        type: String,
        accessToken: String?,
        email: String?,
        password: String?,
        social_id: String?,
        name: String?,
        profile_img: String?,
        fcm_token: String?
    ): Response<LoginResponse> {
        return userAPIService.login(type,accessToken,email,password,social_id,name,profile_img,fcm_token)
    }

    override suspend fun checkEmail(email: String): Response<Boolean> {
        return userAPIService.checkEmail(email)
    }

    override suspend fun signUp(request: SignUpRequest): Response<User> {
        return userAPIService.signUp(request)
    }

    override suspend fun updateFcmToken(updateFcmTokenRequest: UpdateFcmTokenRequest): Response<Boolean> {
        return userAPIService.updateFcmToken(updateFcmTokenRequest)
    }

    override suspend fun sendAuthMessage(phone: PhoneRequest): Response<Boolean> {
        return userAPIService.sendAuthMessage(phone)
    }

    override suspend fun confirmPhone(phone: String, code: String): Response<Boolean> {
        return userAPIService.confirmPhone(phone,code)
    }

    override suspend fun getUserInfo(user_id: Long): Response<UserProfileResponse> {
        return userAPIService.getUserInfo(user_id)
    }

    override suspend fun updateProfileImg(
        file: MultipartBody.Part,
        user_id: Long
    ): Response<UpdateProfileImageResponse> {
        return userAPIService.updateProfileImg(file, user_id)
    }

}