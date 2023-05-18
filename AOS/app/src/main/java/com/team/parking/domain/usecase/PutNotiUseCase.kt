package com.team.parking.domain.usecase

import com.team.parking.data.model.ticket.TicketSellConfirmResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.NotiRepository
import com.team.parking.domain.repository.TicketRepository

class PutNotiUseCase (
    private val notiRepository: NotiRepository
    ) {
        suspend fun execute(
            notiId: Long,
        ): Resource<Boolean> {
            return notiRepository.readNotification(notiId)
        }
}