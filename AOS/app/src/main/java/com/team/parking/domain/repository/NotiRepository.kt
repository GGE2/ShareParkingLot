package com.team.parking.domain.repository

import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.data.util.Resource
import retrofit2.Response
import retrofit2.http.Query

interface NotiRepository {

    suspend fun getNotiList(user_id: Long): Resource<List<GetNotiListRequest>>

    suspend fun readNotification(noti_id: Long): Resource<Boolean>

    suspend fun removeAll(user_id: Long): Resource<Boolean>
}