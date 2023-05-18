package com.team.parking.data.repository

import com.team.parking.data.model.ticket.*
import com.team.parking.data.repository.dataSource.TicketRemoteDataSource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.TicketRepository
import retrofit2.Response

class TicketRepositoryImpl(private val ticketRemoteDataSource: TicketRemoteDataSource) : TicketRepository{
    override suspend fun getTicketAvailable(
        shaId: Long,
        time: Int
    ): Resource<TicketAvailableResponse> {
        return responseToTicketAvailableResponse(ticketRemoteDataSource.getTicketAvailable(shaId, time))
    }

    override suspend fun getTicketDetail(ticketId: Long): Resource<TicketDetailResponse> {
        return responseToTicketDetailResponse(ticketRemoteDataSource.getTicketDetail(ticketId))
    }

    override suspend fun postPurchaseTicket(
        ticketCreateRequest: TicketCreateRequest,
        userId: Long
    ): Resource<TicketCreateResponse> {
        return responseToTicketCreateResponse(ticketRemoteDataSource.postPurchaseTicket(ticketCreateRequest, userId))
    }

    override suspend fun getTicketBoughtList(userId: Long): Resource<List<TicketBoughtListResponse>> {
        return responseToListTicketBoughtListResponse(ticketRemoteDataSource.getTicketBoughtList(userId))
    }

    override suspend fun putTicketBuyConfirm(
        userId: Long,
        ticketId: Long
    ): Resource<TicketBuyConfirmResponse> {
        return responseToTicketBuyConfirmResponse(ticketRemoteDataSource.putTicketBuyConfirm(userId, ticketId))
    }

    override suspend fun putTicketSellConfirm(
        userId: Long,
        ticketId: Long
    ): Resource<TicketSellConfirmResponse> {
        return responseToTicketSellConfirmResponse(ticketRemoteDataSource.putTicketSellConfirm(userId, ticketId))
    }

    override suspend fun getTicketSoldList(
        userId: Long,
        shaId: Long
    ): Resource<List<TicketSoldListResponse>> {
        return responseToListTicketSoldListResponse(ticketRemoteDataSource.getTicketSoldList(userId, shaId))
    }

    private fun responseToTicketAvailableResponse(response: Response<TicketAvailableResponse>): Resource<TicketAvailableResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToTicketDetailResponse(response: Response<TicketDetailResponse>): Resource<TicketDetailResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToTicketCreateResponse(response: Response<TicketCreateResponse>): Resource<TicketCreateResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToListTicketBoughtListResponse(response: Response<List<TicketBoughtListResponse>>): Resource<List<TicketBoughtListResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToTicketBuyConfirmResponse(response: Response<TicketBuyConfirmResponse>): Resource<TicketBuyConfirmResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToTicketSellConfirmResponse(response: Response<TicketSellConfirmResponse>): Resource<TicketSellConfirmResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToListTicketSoldListResponse(response: Response<List<TicketSoldListResponse>>): Resource<List<TicketSoldListResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}