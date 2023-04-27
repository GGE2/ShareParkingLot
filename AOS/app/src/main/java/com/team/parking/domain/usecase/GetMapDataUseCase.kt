package com.team.parking.domain.usecase

import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.MapRepository
import retrofit2.Response

class GetMapDataUseCase(private val mapRepository: MapRepository) {

    suspend fun execute(request: MapRequest) : Resource<List<MapResponse>> {
        return mapRepository.getParkingLots(request)
    }
}