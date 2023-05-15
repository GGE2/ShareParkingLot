package com.team.parking.domain.usecase

import com.team.parking.data.model.day.DayRequest
import com.team.parking.domain.repository.ShareLotRepository

class PutShareLotDayUseCase(
    private val shareLotRepository: ShareLotRepository
) {
    suspend fun execute(daySaveDtos: List<DayRequest>, parkId: Long){
        shareLotRepository.putSaveDay(daySaveDtos, parkId)
    }
}