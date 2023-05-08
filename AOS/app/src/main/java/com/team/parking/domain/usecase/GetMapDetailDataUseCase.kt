package com.team.parking.domain.usecase

import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.MapRepository

class GetMapDetailDataUseCase(
    private val mapRepository: MapRepository
) {
    suspend fun execute(parkId : Int ) : Resource<MapDetailResponse>{
        return mapRepository.getParkingLotDetail(parkId)
    }

}