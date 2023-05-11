package com.team.parking.domain.usecase

import com.team.parking.data.model.ticket.TicketBuyConfirmResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.TicketRepository

class PutTicketBuyConfirmUseCase (
    private val ticketRepository: TicketRepository
) {
    suspend fun execute(
        userId: Long,
        ticketId: Long
    ): Resource<TicketBuyConfirmResponse> {
        return ticketRepository.putTicketBuyConfirm(userId, ticketId)
    }
}