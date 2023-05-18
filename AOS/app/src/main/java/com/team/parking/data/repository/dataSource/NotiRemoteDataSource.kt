package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.notification.GetNotiListRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NotiRemoteDataSource {


    suspend fun getNotiList(user_id: Long): Response<List<GetNotiListRequest>>

    suspend fun readNotification(noti_id: Long): Response<Boolean>

    suspend fun removeAll(user_id: Long): Response<Boolean>
}