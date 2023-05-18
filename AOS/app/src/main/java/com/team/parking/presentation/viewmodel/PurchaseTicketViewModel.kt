package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.data.model.ticket.TicketAvailableResponse
import com.team.parking.data.model.ticket.TicketCreateRequest
import com.team.parking.domain.usecase.GetTicketAvailableUseCase
import com.team.parking.domain.usecase.PostPurchaseTicketUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "PurchaseTicketViewModel"

class PurchaseTicketViewModel(
    private val app: Application,
    private val getTicketAvailableUseCase: GetTicketAvailableUseCase,
    private val postPurchaseTicketUseCase: PostPurchaseTicketUseCase
): AndroidViewModel(app) {
    private val application = app as App

    private var _ticketAvailable = MutableLiveData(TicketAvailableResponse(false, false, false, false))
    val ticketAvailable: LiveData<TicketAvailableResponse> get() = _ticketAvailable

    private var _startHour = MutableLiveData(0)
    val startHour: LiveData<Int> get() = _startHour

    private var _expectedPrice = MutableLiveData(0)
    val expectedPrice: LiveData<Int> get() = _expectedPrice

    val ticketCreateRequest = TicketCreateRequest("", 0, 0, -1)

    fun setExpectedPrice(price: Int){
        _expectedPrice.value = price
    }

    fun setStartHour(hour: Int){
        _startHour.value = hour
    }

    fun getTicketAvailable(shaId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = getTicketAvailableUseCase.execute(shaId, _startHour.value!!)
                _ticketAvailable.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun postTicketAvailable(userId: Long) = viewModelScope.launch(Dispatchers.IO){
        try {
            if(application.isNetworkAvailable(app)){
                var apiResult = postPurchaseTicketUseCase.execute(ticketCreateRequest, userId)
                Log.d(TAG, "postTicketAvailable: ${apiResult.data!!.ticketId}")
                Log.d(TAG, "postTicketAvailable: ${apiResult.data!!.cost}")
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }
}