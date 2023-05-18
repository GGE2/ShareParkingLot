package com.team.parking.data.api

import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.data.model.user.LoginResponse
import com.team.parking.data.model.user.SignUpRequest
import com.team.parking.data.model.user.User
import retrofit2.Response
import retrofit2.http.*

interface NotiAPIService {
    @GET("noti/list")
    suspend fun getNotiList(@Query("user_id")user_id : Long) : Response<List<GetNotiListRequest> >

    @PUT("noti/status")
    suspend fun readNotification(@Query("noti_id") noti_id : Long) : Response<Boolean>

    @DELETE("noti/all")
    suspend fun removeAll(@Query("user_id") user_id: Long): Response<Boolean >

}