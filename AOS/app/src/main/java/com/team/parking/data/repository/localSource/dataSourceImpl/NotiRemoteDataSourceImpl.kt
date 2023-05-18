package com.team.parking.data.repository.localSource.dataSourceImpl

import com.team.parking.data.api.NotiAPIService
import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.data.repository.dataSource.NotiRemoteDataSource
import retrofit2.Response

class NotiRemoteDataSourceImpl (
    private val notiAPIService: NotiAPIService
        ):NotiRemoteDataSource{
    override suspend fun getNotiList(user_id: Long): Response<List<GetNotiListRequest>> {
        return notiAPIService.getNotiList(user_id)
    }

    override suspend fun readNotification(noti_id: Long): Response<Boolean> {
        return notiAPIService.readNotification(noti_id)
    }

    override suspend fun removeAll(user_id: Long): Response<Boolean> {
        return notiAPIService.removeAll(user_id)
    }

}