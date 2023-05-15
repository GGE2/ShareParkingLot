package com.team.parking.domain.usecase

import com.team.parking.data.model.ticket.TicketAvailableResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.TicketRepository

class GetTicketAvailableUseCase (
    private val ticketRepository: TicketRepository
) {
    suspend fun execute(
        shaId: Long,
        time: Int
    ): Resource<TicketAvailableResponse> {
        return ticketRepository.getTicketAvailable(shaId, time)
    }
}