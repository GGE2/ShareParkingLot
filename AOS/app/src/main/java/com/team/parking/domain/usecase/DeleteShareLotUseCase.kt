package com.team.parking.domain.usecase

import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.ShareLotRepository

class DeleteShareLotUseCase(
    private val shareLotRepository: ShareLotRepository
) {
    suspend fun execute(parkId: Long): Resource<Unit> {
        return shareLotRepository.deleteShareLot(parkId)
    }
}