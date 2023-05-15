package com.team.parking.domain.usecase

import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.ShareLotRepository

class GetShareLotDayUseCase(
    private val shareLotRepository: ShareLotRepository
) {
    suspend fun execute(parkId: Long): Resource<List<DayRequest>> {
        return shareLotRepository.getShareLotDay(parkId)
    }
}