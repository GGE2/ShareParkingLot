package com.team.parking.domain.usecase

import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.ShareLotRepository

class GetShareLotListUseCase(
    private val shareLotRepository: ShareLotRepository
) {
    suspend fun execute(userId: Long): Resource<List<ShareLotResponse>> {
        return shareLotRepository.getShareLotList(userId)
    }
}