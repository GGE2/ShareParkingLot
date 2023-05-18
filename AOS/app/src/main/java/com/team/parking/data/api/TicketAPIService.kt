package com.team.parking.data.api

import com.team.parking.data.model.ticket.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketAPIService {
    @GET("ticket/{shaId}/{time}")
    suspend fun getTicketAvailable(@Path("shaId") shaId: Long, @Path("time") time: Int) : Response<TicketAvailableResponse>

    @GET("ticket/{ticketId}")
    suspend fun getTicketDetail(@Path("ticketId") ticketId: Long) : Response<TicketDetailResponse>

    @POST("ticket/{userId}")
    suspend fun postPurchaseTicket(@Body ticketCreateRequest: TicketCreateRequest, @Path("userId") userId: Long) : Response<TicketCreateResponse>

    @GET("ticket/bought/{userId}")
    suspend fun getTicketBoughtList(@Path("userId") userId: Long) : Response<List<TicketBoughtListResponse>>

    @PUT("ticket/buy/{userId}/{ticketId}")
    suspend fun putTicketBuyConfirm(@Path("userId") userId: Long, @Path("ticketId") ticketId: Long) : Response<TicketBuyConfirmResponse>

    @PUT("ticket/sell/{userId}/{ticketId}")
    suspend fun putTicketSellConfirm(@Path("userId") userId: Long, @Path("ticketId") ticketId: Long) : Response<TicketSellConfirmResponse>

    @GET("ticket/sold/{userId}/{shaId}")
    suspend fun getTicketSoldList(@Path("userId") userId: Long, @Path("shaId") shaId: Long) : Response<List<TicketSoldListResponse>>
}