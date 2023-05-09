package com.team.parking.data.repository

import com.team.parking.data.model.point.ChargePointResponse
import com.team.parking.data.model.point.EarnedPointResponse
import com.team.parking.data.model.point.SpentPointResponse
import com.team.parking.data.repository.dataSource.PointRemoteDataSource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.PointRepository
import retrofit2.Response

private const val TAG = "PointRepositoryImpl"

class PointRepositoryImpl(
    private val pointRemoteDataSource: PointRemoteDataSource
) : PointRepository {
    override suspend fun getCurrentPoint(userId: Long): Resource<Int> {
        return responseToInt(pointRemoteDataSource.getCurrentPoint(userId))
    }

    override suspend fun putChargePoint(
        userId: Long,
        ptCharge: Int
    ): Resource<ChargePointResponse> {
        return responseToChargePointResponse(pointRemoteDataSource.putChargePoint(userId, ptCharge))
    }

    override suspend fun getEarnedPoint(
        userId: Long,
        year: String,
        month: String
    ): Resource<List<EarnedPointResponse>> {
        return responseToListEarnedPointResponse(pointRemoteDataSource.getEarnedPoint(userId, year, month))
    }

    override suspend fun getSpentPoint(
        userId: Long,
        year: String,
        month: String
    ): Resource<List<SpentPointResponse>> {
        return responseToListSpentPointResponse(pointRemoteDataSource.getSpentPoint(userId, year, month))
    }

    private fun responseToInt(response: Response<Int>): Resource<Int> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToChargePointResponse(response: Response<ChargePointResponse>): Resource<ChargePointResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToListEarnedPointResponse(response: Response<List<EarnedPointResponse>>): Resource<List<EarnedPointResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToListSpentPointResponse(response: Response<List<SpentPointResponse>>): Resource<List<SpentPointResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}