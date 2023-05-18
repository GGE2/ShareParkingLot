package com.team.parking.domain.repository

import com.team.parking.data.model.ticket.*
import com.team.parking.data.util.Resource

interface TicketRepository {
    suspend fun getTicketAvailable(shaId: Long, time: Int) : Resource<TicketAvailableResponse>
    suspend fun getTicketDetail(ticketId: Long) : Resource<TicketDetailResponse>
    suspend fun postPurchaseTicket(ticketCreateRequest: TicketCreateRequest, userId: Long) : Resource<TicketCreateResponse>
    suspend fun getTicketBoughtList(userId: Long) : Resource<List<TicketBoughtListResponse>>
    suspend fun putTicketBuyConfirm(userId: Long, ticketId: Long) : Resource<TicketBuyConfirmResponse>
    suspend fun putTicketSellConfirm(userId: Long, ticketId: Long) : Resource<TicketSellConfirmResponse>
    suspend fun getTicketSoldList(userId: Long, shaId: Long) : Resource<List<TicketSoldListResponse>>
}