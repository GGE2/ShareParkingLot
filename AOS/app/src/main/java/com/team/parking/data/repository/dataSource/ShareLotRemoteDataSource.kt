package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.model.parkinglot.ShareLotResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface ShareLotRemoteDataSource {
    suspend fun deleteShareLot(parkId: Long) : Response<Unit>
    suspend fun getShareLotDetail(parkId: Long, userId: Long) : Response<MapDetailResponse>
    suspend fun getShareLotList(userId: Long) : Response<List<ShareLotResponse>>
    suspend fun getShareLotDay(parkId: Long) : Response<List<DayRequest>>
    suspend fun postShareLot(
        saveDto: ShareLotRequest,
        files: List<MultipartBody.Part>
    ) : Response<Long>
    suspend fun putSaveDay(
        daySaveDtos: List<DayRequest>,
        parkId: Long
    ) : Response<Unit>
}