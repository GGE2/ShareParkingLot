package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.domain.usecase.GetCurrentPointUseCase
import com.team.parking.domain.usecase.PutChargePointUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "PointViewModel"

class PointViewModel(
    private val app: Application,
    private val getCurrentPointUseCase: GetCurrentPointUseCase,
    private val putChargePointUseCase: PutChargePointUseCase

) : AndroidViewModel(app) {
    val application = app as App

    private var _point : MutableLiveData<Int> = MutableLiveData(1000)
    val point : LiveData<Int> get() = _point

    private var _ptHas : MutableLiveData<Int> = MutableLiveData(0)
    val ptHas : LiveData<Int> get() = _ptHas

    fun setPoint(point: Int){
        _point.value = point
    }

    fun getCurrentPoint(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = getCurrentPointUseCase.execute(userId)
                _ptHas.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun putChargePoint(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = putChargePointUseCase.execute(userId, point.value!!)
                _ptHas.postValue(apiResult.data?.ptHas)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }
}