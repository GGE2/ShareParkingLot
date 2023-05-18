package com.team.parking.domain.usecase

import com.team.parking.data.model.ticket.TicketBoughtListResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.TicketRepository

class GetTicketBoughtListUseCase (
    private val ticketRepository: TicketRepository
) {
    suspend fun execute(
        userId: Long
    ): Resource<List<TicketBoughtListResponse>> {
        return ticketRepository.getTicketBoughtList(userId)
    }
}