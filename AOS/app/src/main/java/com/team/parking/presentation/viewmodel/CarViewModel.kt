package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.data.model.car.CarListResponse
import com.team.parking.data.model.car.CarSaveDto
import com.team.parking.domain.usecase.GetCarListUseCase
import com.team.parking.domain.usecase.PostCarUseCase
import com.team.parking.domain.usecase.SetRepCarUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "CarViewModel"

class CarViewModel(
    private val app: Application,
    private val setRepCarUseCase: SetRepCarUseCase,
    private val getCarListUseCase: GetCarListUseCase,
    private val postCarUseCase: PostCarUseCase
) : AndroidViewModel(app) {
    val application = app as App

    private var _carList = MutableLiveData<List<CarListResponse>>(listOf())
    val carList get() = _carList

    var selectedCar: CarListResponse? = null

    fun setRepCar(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = setRepCarUseCase.execute(selectedCar!!.carId, userId)
                selectedCar = null
                delay(500)
                getCarList(userId)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun getCarList(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = getCarListUseCase.execute(userId)
                _carList.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun postCar(carNumber: String, userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = postCarUseCase.execute(CarSaveDto(carNumber), userId)
                delay(500)
                getCarList(userId)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }
}