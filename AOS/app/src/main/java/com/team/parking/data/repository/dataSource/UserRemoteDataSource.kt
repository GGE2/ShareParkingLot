package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.user.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserRemoteDataSource {

    suspend fun login(type: String,
              accessToken : String?=null,
              email : String? = null,
              password : String?=null,
              social_id : String?=null,
              name : String?=null,
              profile_img : String?=null,
              fcm_token : String?=null,
    ): Response<LoginResponse>


    suspend fun checkEmail(email: String) : Response<Boolean>

    suspend fun signUp(request: SignUpRequest) : Response<User>


    suspend fun updateFcmToken(updateFcmTokenRequest : UpdateFcmTokenRequest) : Response<Boolean>


    suspend fun  sendAuthMessage(phone : PhoneRequest) : Response<Boolean>


    suspend fun confirmPhone(phone : String, code : String) : Response<Boolean>

    suspend fun getUserInfo(user_id : Long) : Response<UserProfileResponse>

    suspend fun updateProfileImg(file: MultipartBody.Part, user_id : Long) : Response<UpdateProfileImageResponse>
}