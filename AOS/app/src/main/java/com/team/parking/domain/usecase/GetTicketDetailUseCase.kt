package com.team.parking.domain.usecase

import com.team.parking.data.model.ticket.TicketDetailResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.TicketRepository

class GetTicketDetailUseCase (
    private val ticketRepository: TicketRepository
) {
    suspend fun execute(
        ticketId: Long
    ): Resource<TicketDetailResponse> {
        return ticketRepository.getTicketDetail(ticketId)
    }
}