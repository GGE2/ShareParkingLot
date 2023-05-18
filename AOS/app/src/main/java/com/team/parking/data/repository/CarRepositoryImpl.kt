package com.team.parking.data.repository

import com.team.parking.data.model.car.CarListResponse
import com.team.parking.data.model.car.CarSaveDto
import com.team.parking.data.repository.dataSource.CarRemoteDataSource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.CarRepository
import retrofit2.Response

class CarRepositoryImpl(
    private val carRemoteDataSource: CarRemoteDataSource
) : CarRepository{
    override suspend fun setRepCar(carId: Long, userId: Long): Resource<Boolean> {
        return responseToBoolean(carRemoteDataSource.setRepCar(carId, userId))
    }

    override suspend fun getCarList(userId: Long): Resource<List<CarListResponse>> {
        return responseToListCarListResponse(carRemoteDataSource.getCarList(userId))
    }

    override suspend fun postCar(carSaveDto: CarSaveDto, userId: Long): Resource<Unit> {
        return responseToUnit(carRemoteDataSource.postCar(carSaveDto, userId))
    }

    private fun responseToBoolean(response: Response<Boolean>): Resource<Boolean> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToListCarListResponse(response: Response<List<CarListResponse>>): Resource<List<CarListResponse>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToUnit(response: Response<Unit>): Resource<Unit> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}