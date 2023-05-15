package com.team.parking.domain.usecase

import com.team.parking.data.model.ticket.TicketCreateRequest
import com.team.parking.data.model.ticket.TicketCreateResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.TicketRepository

class PostPurchaseTicketUseCase (
    private val ticketRepository: TicketRepository
) {
    suspend fun execute(
        ticketCreateRequest: TicketCreateRequest,
        userId: Long
    ): Resource<TicketCreateResponse> {
        return ticketRepository.postPurchaseTicket(ticketCreateRequest, userId)
    }
}