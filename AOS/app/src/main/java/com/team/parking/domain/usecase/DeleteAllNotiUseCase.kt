package com.team.parking.domain.usecase

import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.NotiRepository

class DeleteAllNotiUseCase(
    private val notiRepository: NotiRepository
) {
    suspend fun execute(
        userId: Long,
    ): Resource<Boolean> {
        return notiRepository.removeAll(userId);
    }
}