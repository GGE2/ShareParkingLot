package com.team.parking.data.api

import com.team.parking.data.model.user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface UserService {
    @GET("user/login")
    fun login(@Query("type") type: String,
                      @Query("accessToken")accessToken : String?=null,
                      @Query("email") email : String? = null,
                      @Query("password") password : String?=null,
                      @Query("social_id") social_id : String?=null,
                      @Query("name") name : String?=null,
                      @Query("profile_img") profile_img : String?=null,
                      @Query("fcm_token") fcm_token : String?=null,
    ): Call<LoginResponse>

    @GET("user/email")
    suspend fun checkEmail(@Query("email")email: String) : Response<Boolean>

    @POST("user/sign")
    suspend fun signUp(@Body request: SignUpRequest) : Response<User>

    @POST("user/fcm-token")
    suspend fun updateFcmToken(@Body updateFcmTokenRequest : UpdateFcmTokenRequest) : Response<Boolean>

}