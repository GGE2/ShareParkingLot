package com.team.parking.domain.usecase

import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.NotiRepository

class GetNotiListUseCase(
    private val notiRepository: NotiRepository
) {
    suspend fun execute(
        userId: Long,
    ): Resource<List<GetNotiListRequest>> {
        return notiRepository.getNotiList(userId)
    }
}