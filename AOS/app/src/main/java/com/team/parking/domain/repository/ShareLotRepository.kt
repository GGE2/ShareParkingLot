package com.team.parking.domain.repository

import com.team.parking.data.model.day.DayRequest
import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.data.util.Resource
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Query

interface ShareLotRepository {
    suspend fun deleteShareLot(parkId: Long) : Resource<Unit>
    suspend fun getShareLotDetail(parkId: Long, userId: Long) : Resource<MapDetailResponse>
    suspend fun getShareLotList(userId: Long) : Resource<List<ShareLotResponse>>
    suspend fun getShareLotDay(parkId: Long) : Resource<List<DayRequest>>
    suspend fun postShareLot(
        saveDto: ShareLotRequest,
        files: List<MultipartBody.Part>
    ) : Resource<Long>
    suspend fun putSaveDay(
        daySaveDtos: List<DayRequest>,
        parkId: Long
    ) : Resource<Unit>
}