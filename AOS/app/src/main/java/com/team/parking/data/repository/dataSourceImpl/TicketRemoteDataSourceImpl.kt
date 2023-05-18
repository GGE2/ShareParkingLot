package com.team.parking.data.repository.dataSourceImpl

import com.team.parking.data.api.TicketAPIService
import com.team.parking.data.model.ticket.*
import com.team.parking.data.repository.dataSource.TicketRemoteDataSource
import retrofit2.Response

class TicketRemoteDataSourceImpl(private val ticketAPIService: TicketAPIService) : TicketRemoteDataSource{
    override suspend fun getTicketAvailable(
        shaId: Long,
        time: Int
    ): Response<TicketAvailableResponse> {
        return ticketAPIService.getTicketAvailable(shaId, time)
    }

    override suspend fun getTicketDetail(ticketId: Long): Response<TicketDetailResponse> {
        return ticketAPIService.getTicketDetail(ticketId)
    }

    override suspend fun postPurchaseTicket(
        ticketCreateRequest: TicketCreateRequest,
        userId: Long
    ): Response<TicketCreateResponse> {
        return ticketAPIService.postPurchaseTicket(ticketCreateRequest, userId)
    }

    override suspend fun getTicketBoughtList(userId: Long): Response<List<TicketBoughtListResponse>> {
        return ticketAPIService.getTicketBoughtList(userId)
    }

    override suspend fun putTicketBuyConfirm(
        userId: Long,
        ticketId: Long
    ): Response<TicketBuyConfirmResponse> {
        return ticketAPIService.putTicketBuyConfirm(userId, ticketId)
    }

    override suspend fun putTicketSellConfirm(
        userId: Long,
        ticketId: Long
    ): Response<TicketSellConfirmResponse> {
        return ticketAPIService.putTicketSellConfirm(userId, ticketId)
    }

    override suspend fun getTicketSoldList(
        userId: Long,
        shaId: Long
    ): Response<List<TicketSoldListResponse>> {
        return ticketAPIService.getTicketSoldList(userId, shaId)
    }
}