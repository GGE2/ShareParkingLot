package com.team.parking.data.repository.dataSourceImpl

import com.team.parking.data.api.ShareLotAPIService
import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.data.repository.dataSource.ShareLotRemoteDataSource
import okhttp3.MultipartBody
import retrofit2.Response

class ShareLotRemoteDataSourceImpl(private val shareLotAPIService: ShareLotAPIService) : ShareLotRemoteDataSource {
    override suspend fun deleteShareLot(parkId: Long): Response<Unit> {
        return shareLotAPIService.deleteShareLot(parkId)
    }

    override suspend fun getShareLotDetail(parkId: Long, userId: Long): Response<MapDetailResponse> {
        return shareLotAPIService.getShareLotDetail(parkId, userId)
    }

    override suspend fun getShareLotList(userId: Long): Response<List<ShareLotResponse>> {
        return shareLotAPIService.getShareLotList(userId)
    }

    override suspend fun postShareLot(
        saveDto: ShareLotRequest,
        files: List<MultipartBody.Part>
    ) : Response<Long> {
        return shareLotAPIService.postShareLot(saveDto, files)
    }

    override suspend fun putSaveDay(daySaveDtos: List<DayRequest>, parkId: Long) : Response<Unit> {
        return shareLotAPIService.putSaveDay(daySaveDtos, parkId)
    }

    override suspend fun getShareLotDay(parkId: Long): Response<List<DayRequest>> {
        return shareLotAPIService.getShareLotDay(parkId)
    }
}