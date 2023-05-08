package com.team.parking.presentation.viewmodel

import android.app.Application
import android.content.res.Resources
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.R
import com.team.parking.data.model.day.DayRequest
import com.team.parking.domain.usecase.GetShareLotDayUseCase
import com.team.parking.domain.usecase.PutShareLotDayUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "DaySelectViewModel"

class DaySelectViewModel(
    private val app: Application,
    private val getShareLotDayUseCase: GetShareLotDayUseCase,
    private val putShareLotDayUseCase: PutShareLotDayUseCase
): AndroidViewModel(app) {
    val application = App()
    var add: Boolean = false
    private var _currentIndex: MutableLiveData<Int> = MutableLiveData(0)
    val currentIndex: LiveData<Int> get() = _currentIndex
    var layoutList: List<View> = listOf()
    private var _dayList: MutableLiveData<List<DayRequest>> = MutableLiveData(listOf(
        DayRequest(-1,-1,"Mon",false),
        DayRequest(-1,-1,"Tue",false),
        DayRequest(-1,-1,"Wed",false),
        DayRequest(-1,-1,"Thurs",false),
        DayRequest(-1,-1,"Fri",false),
        DayRequest(-1,-1,"Sat",false),
        DayRequest(-1,-1,"Sun",false)
    ))
    val dayList: LiveData<List<DayRequest>> get() = _dayList
    var resource: Resources = Resources.getSystem()

    fun setCurrentIndex(index: Int){
        _currentIndex.value = index
    }

    fun initDayList(){
        _dayList.value = listOf(
            DayRequest(-1,-1,"Mon",false),
            DayRequest(-1,-1,"Tue",false),
            DayRequest(-1,-1,"Wed",false),
            DayRequest(-1,-1,"Thurs",false),
            DayRequest(-1,-1,"Fri",false),
            DayRequest(-1,-1,"Sat",false),
            DayRequest(-1,-1,"Sun",false)
        )
    }

    fun unselectAllLayout(){
        for(layout in layoutList){
            layout.background = ResourcesCompat.getDrawable(resource, R.drawable.day_unselected_background, null)
        }
    }

    fun getShareLotDay(parkId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = getShareLotDayUseCase.execute(parkId)
                _dayList.postValue(apiResult.data!!)
                setCurrentIndex(1)
                setCurrentIndex(0)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun putShareLotDay(parkId: Long) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = putShareLotDayUseCase.execute(_dayList.value!!, parkId)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }
}