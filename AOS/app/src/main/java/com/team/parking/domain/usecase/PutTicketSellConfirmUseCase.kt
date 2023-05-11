package com.team.parking.domain.usecase

import com.team.parking.data.model.ticket.TicketSellConfirmResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.TicketRepository

class PutTicketSellConfirmUseCase (
    private val ticketRepository: TicketRepository
) {
    suspend fun execute(
        userId: Long,
        ticketId: Long
    ): Resource<TicketSellConfirmResponse> {
        return ticketRepository.putTicketSellConfirm(userId, ticketId)
    }
}