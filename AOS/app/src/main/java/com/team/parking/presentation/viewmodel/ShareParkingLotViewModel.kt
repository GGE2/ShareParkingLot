package com.team.parking.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotRequest
import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.usecase.DeleteShareLotUseCase
import com.team.parking.domain.usecase.GetShareLotListUseCase
import com.team.parking.domain.usecase.PostShareLotUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "ShareParkingLotViewMode"

class ShareParkingLotViewModel(
    private val app: Application, private val postShareLotUseCase: PostShareLotUseCase,
    private val getShareLotListUseCase: GetShareLotListUseCase,
    private val deleteShareLotUseCase: DeleteShareLotUseCase
) : AndroidViewModel(app) {
    private var _uriList: MutableLiveData<MutableList<Uri>> = MutableLiveData(mutableListOf())
    val uriList: LiveData<MutableList<Uri>> get() = _uriList

    private var _shareLotList: MutableLiveData<List<ShareLotResponse>?> = MutableLiveData(
        listOf()
    )
    val shareLotList: LiveData<List<ShareLotResponse>?> get() = _shareLotList

    var sharelotId: Long = -2


    val application = App()

    var multipartBodyList: MutableList<MultipartBody.Part> = mutableListOf()

    fun clearList(){
        _uriList.value?.clear()
        multipartBodyList.clear()
    }

    fun addImage(uri: Uri){
        _uriList.value?.add(uri)
        _uriList.value = _uriList.value
    }

    fun post(shaType: Int,
             shaName: String,
             jibun: String,
             road: String,
             shaFee: Int,
             shaProp: String?,
             latitude: Float,
             longitude: Float, userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try{
            if(application.isNetworkAvailable(app)){
                var shareLotRequest = ShareLotRequest(shaType = shaType,
                    shaName = shaName,
                    jibun = jibun,
                    road = road,
                    shaFee = shaFee,
                    shaProp = shaProp,
                latitude = latitude,
                longitude = longitude,
                userId = userId)
                val apiResult = postShareLotUseCase.execute(shareLotRequest, multipartBodyList.toList())
                sharelotId = apiResult.data ?: -2
                Log.d(TAG, "post: $sharelotId")
            }else{
                Log.d(TAG, "post: 네트워크 문제")
            }

        }catch(e:Exception){
            Log.d(TAG, "post: ${e.message}")
        }
    }

    fun getShareLotList(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = getShareLotListUseCase.execute(userId)
                _shareLotList.postValue(apiResult.data)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun deleteShareLot(parkId: Long, userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = deleteShareLotUseCase.execute(parkId)
                delay(500)
                getShareLotList(userId)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }
}