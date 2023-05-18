package com.team.parking.domain.usecase

import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.ShareLotRepository
import okhttp3.MultipartBody

class PostShareLotUseCase(
    private val shareLotRepository: ShareLotRepository
) {
    suspend fun execute(saveDto: ShareLotRequest,
                        files: List<MultipartBody.Part>): Resource<Long> {
        return shareLotRepository.postShareLot(saveDto, files)
    }
}