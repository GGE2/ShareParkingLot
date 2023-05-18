package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.data.model.ticket.TicketBoughtListResponse
import com.team.parking.data.model.ticket.TicketSoldListResponse
import com.team.parking.domain.usecase.GetTicketBoughtListUseCase
import com.team.parking.domain.usecase.GetTicketSoldListUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MyTicketViewModel"

class MyTicketViewModel(
    private val app: Application,
    private val getTicketBoughtListUseCase: GetTicketBoughtListUseCase,
    private val getTicketSoldListUseCase: GetTicketSoldListUseCase
): AndroidViewModel(app) {
    val application = app as App

    var bought = false

    private var _boughtList = MutableLiveData<List<TicketBoughtListResponse>>(listOf())
    val boughtList: LiveData<List<TicketBoughtListResponse>> get() = _boughtList

    private var _soldList = MutableLiveData<List<TicketSoldListResponse>>(listOf())
    val soldList: LiveData<List<TicketSoldListResponse>> get() = _soldList

    fun getBoughtList(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = getTicketBoughtListUseCase.execute(userId)
                _boughtList.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun getSoldList(userId: Long, shaId: Long) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if(application.isNetworkAvailable(app)){
                Log.d(TAG, "getSoldList: ${userId} ${shaId}")
                var apiResult = getTicketSoldListUseCase.execute(userId, shaId)
                _soldList.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }
}