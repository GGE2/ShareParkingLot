package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.data.model.ticket.TicketDetailResponse
import com.team.parking.domain.usecase.GetTicketDetailUseCase
import com.team.parking.domain.usecase.PutTicketBuyConfirmUseCase
import com.team.parking.domain.usecase.PutTicketSellConfirmUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "TicketDetailViewModel"

class TicketDetailViewModel (
    private val app: Application,
    private val getTicketDetailUseCase: GetTicketDetailUseCase,
    private val putTicketBuyConfirmUseCase: PutTicketBuyConfirmUseCase,
    private val putTicketSellConfirmUseCase: PutTicketSellConfirmUseCase
) : AndroidViewModel(app) {
    val application = app as App
    var ticketId: Long = 0
    var buyer = false

    private var _ticketDetail = MutableLiveData<TicketDetailResponse>()
    val ticketDetail : LiveData<TicketDetailResponse> get() = _ticketDetail

    fun getTicketDetail() = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = getTicketDetailUseCase.execute(ticketId)
                _ticketDetail.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun putTicketBuyConfirm(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = putTicketBuyConfirmUseCase.execute(userId, ticketId)
                Log.d(TAG, "putTicketBuyConfirm: ${apiResult.data?.shaId}")
                delay(500)
                getTicketDetail()
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun putTicketSellConfirm(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = putTicketSellConfirmUseCase.execute(userId, ticketId)
                Log.d(TAG, "putTicketSellConfirm: ${apiResult.data?.shaId}")
                delay(500)
                getTicketDetail()
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }
}