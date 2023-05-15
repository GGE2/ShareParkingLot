package com.team.parking.domain.usecase

import com.team.parking.data.model.ticket.TicketBoughtListResponse
import com.team.parking.data.model.ticket.TicketSoldListResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.TicketRepository

class GetTicketSoldListUseCase (
    private val ticketRepository: TicketRepository
) {
    suspend fun execute(
        userId: Long,
        shaId: Long
    ): Resource<List<TicketSoldListResponse>> {
        return ticketRepository.getTicketSoldList(userId, shaId)
    }
}