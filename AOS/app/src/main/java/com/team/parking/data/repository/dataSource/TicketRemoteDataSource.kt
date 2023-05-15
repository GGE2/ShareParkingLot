package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.ticket.*
import retrofit2.Response

interface TicketRemoteDataSource {
    suspend fun getTicketAvailable(shaId: Long, time: Int) : Response<TicketAvailableResponse>
    suspend fun getTicketDetail(ticketId: Long) : Response<TicketDetailResponse>
    suspend fun postPurchaseTicket(ticketCreateRequest: TicketCreateRequest, userId: Long) : Response<TicketCreateResponse>
    suspend fun getTicketBoughtList(userId: Long) : Response<List<TicketBoughtListResponse>>
    suspend fun putTicketBuyConfirm(userId: Long, ticketId: Long) : Response<TicketBuyConfirmResponse>
    suspend fun putTicketSellConfirm(userId: Long, ticketId: Long) : Response<TicketSellConfirmResponse>
    suspend fun getTicketSoldList(userId: Long, shaId: Long) : Response<List<TicketSoldListResponse>>
}