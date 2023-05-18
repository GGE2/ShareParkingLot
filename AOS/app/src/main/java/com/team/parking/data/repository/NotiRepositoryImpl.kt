package com.team.parking.data.repository

import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.data.repository.dataSource.NotiRemoteDataSource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.NotiRepository
import retrofit2.Response

class NotiRepositoryImpl (
    private val notiRemoteDataSource : NotiRemoteDataSource
        ) : NotiRepository{
    override suspend fun getNotiList(user_id: Long): Resource<List<GetNotiListRequest>> {
        return responseToResource(notiRemoteDataSource.getNotiList(user_id))
    }

    override suspend fun readNotification(noti_id: Long): Resource<Boolean> {
        return responseToResource(notiRemoteDataSource.readNotification(noti_id))
    }

    override suspend fun removeAll(user_id: Long): Resource<Boolean> {
        return responseToResource(notiRemoteDataSource.removeAll(user_id))
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