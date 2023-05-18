package com.team.parking.data.repository

import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.model.user.*
import com.team.parking.data.repository.dataSource.UserRemoteDataSource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Part
import retrofit2.http.Query

class UserRepositoryImpl (
    private val userRemoteDataSource : UserRemoteDataSource
) : UserRepository{
    override suspend fun login(
        type: String,
        accessToken: String?,
        email: String?,
        password: String?,
        social_id: String?,
        name: String?,
        profile_img: String?,
        fcm_token: String?
    ): Resource<LoginResponse> {
        return responseToResource(userRemoteDataSource.login(type,accessToken,email,password,social_id,name,profile_img,fcm_token))
    }

    override suspend fun checkEmail(email: String): Resource<Boolean> {
        return responseToResource(userRemoteDataSource.checkEmail(email))
    }

    override suspend fun signUp(request: SignUpRequest): Resource<User> {
        return responseToResource(userRemoteDataSource.signUp(request))
    }

    override suspend fun updateFcmToken(updateFcmTokenRequest: UpdateFcmTokenRequest): Resource<Boolean> {
        return responseToResource(userRemoteDataSource.updateFcmToken(updateFcmTokenRequest))
    }

    override suspend fun sendAuthMessage(phone: PhoneRequest): Resource<Boolean> {
        return responseToResource(userRemoteDataSource.sendAuthMessage(phone))
    }

    override suspend fun confirmPhone(phone: String, code: String): Resource<Boolean> {
        return responseToResource(userRemoteDataSource.confirmPhone(phone, code))
    }

    override suspend fun getUserInfo(user_id : Long) : Resource<UserProfileResponse>{
        return responseToResource(userRemoteDataSource.getUserInfo(user_id))
    }

    override suspend fun updateProfileImg(file: MultipartBody.Part, user_id : Long) : Resource<UpdateProfileImageResponse>{
        return responseToResource(userRemoteDataSource.updateProfileImg(file,user_id))
    }

    private fun <T> responseToResource(response: Response<out T>): Resource<T> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}