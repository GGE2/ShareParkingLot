package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.team.parking.data.model.point.EarnedPointResponse
import com.team.parking.data.model.point.SpentPointResponse
import com.team.parking.domain.usecase.GetEarnedPointUseCase
import com.team.parking.domain.usecase.GetSpentPointUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "TransactionHistoryViewM"

class TransactionHistoryViewModel(
    private val app: Application,
    private val getEarnedPointUseCase: GetEarnedPointUseCase,
    private val getSpentPointUseCase: GetSpentPointUseCase
) : AndroidViewModel(app) {
    var earned = true

    val application = app as App

    val calendar = Calendar.getInstance()

    private var _month = MutableLiveData<Int>()
    val month : LiveData<Int> get() = _month

    private var _year = MutableLiveData<Int>()
    val year : LiveData<Int> get() = _year

    private var _earnedPointList = MutableLiveData<List<EarnedPointResponse>>(listOf())
    val earnedPointList get() = _earnedPointList

    private var _spentPointList = MutableLiveData<List<SpentPointResponse>>(listOf())
    val spentPointList get() = _spentPointList

    fun setYear(year: Int){
        _year.value = year
    }

    fun setMonth(month: Int){
        _month.value = month
    }

    fun setPreviousMonth(){
        if(_month.value == 1){
            _year.value = _year.value?.minus(1)
            _month.value = 12
        } else {
            _month.value = _month.value?.minus(1)
        }
        calendar.set(Calendar.MONTH, month.value!! - 1)
        calendar.set(Calendar.YEAR, year.value!!)
    }

    fun setNextMonth(){
        if(_month.value == 12){
            _year.value = _year.value?.plus(1)
            _month.value = 1
        } else {
            _month.value = _month.value?.plus(1)
        }
        calendar.set(Calendar.MONTH, month.value!! - 1)
        calendar.set(Calendar.YEAR, year.value!!)
    }

    fun getEarnedPoint(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        val yearFormatter = SimpleDateFormat("yyyy")
        val monthFormatter = SimpleDateFormat("MM")
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = getEarnedPointUseCase.execute(userId,
                    yearFormatter.format(calendar.time),
                    monthFormatter.format(calendar.time)
                )
                _earnedPointList.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun getSpentPoint(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        val yearFormatter = SimpleDateFormat("yyyy")
        val monthFormatter = SimpleDateFormat("MM")
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = getSpentPointUseCase.execute(userId,
                    yearFormatter.format(calendar.time),
                    monthFormatter.format(calendar.time)
                )
                _spentPointList.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }
}