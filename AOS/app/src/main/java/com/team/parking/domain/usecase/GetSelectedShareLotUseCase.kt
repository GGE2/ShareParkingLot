package com.team.parking.domain.usecase

import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.ShareLotRepository

class GetSelectedShareLotUseCase(
    private val sharedLotRepository: ShareLotRepository
) {
    suspend fun execute(parkId: Long, userId: Long) : Resource<MapDetailResponse> = sharedLotRepository.getShareLotDetail(parkId, userId)
}