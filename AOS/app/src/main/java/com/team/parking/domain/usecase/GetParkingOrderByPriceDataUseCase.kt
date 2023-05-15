package com.team.parking.domain.usecase

import com.team.parking.data.model.map.MapOrderResponse
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.MapRepository

class GetParkingOrderByPriceDataUseCase(
    private val mapRepository: MapRepository
) {
    suspend fun execute(mapRequest: MapRequest) : Resource<List<MapOrderResponse>> = mapRepository.getParkingLotOrderByPrice(mapRequest)
}