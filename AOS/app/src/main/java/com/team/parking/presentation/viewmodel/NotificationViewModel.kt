package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.data.model.notification.GetNotiListRequest
import com.team.parking.domain.usecase.DeleteAllNotiUseCase
import com.team.parking.domain.usecase.GetNotiListUseCase
import com.team.parking.domain.usecase.PutNotiUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val app: Application,
    private val getNotiListUseCase: GetNotiListUseCase,
    private val putNotiUseCase: PutNotiUseCase,
    private val deleteAllNotiUseCase: DeleteAllNotiUseCase
) : AndroidViewModel(app) {


    val application = app as App

    private var _notiList : MutableLiveData<List<GetNotiListRequest>?> = MutableLiveData()
    val notiList : MutableLiveData<List<GetNotiListRequest>?> get() = _notiList



    fun deleteAllNoti(user_id : Long) = viewModelScope.launch(Dispatchers.IO) {
        val result = deleteAllNotiUseCase.execute(user_id)

        _notiList.postValue(listOf())
    }

    fun getNotiList(user_id : Long) =viewModelScope.launch(Dispatchers.IO){
        val result = getNotiListUseCase.execute(user_id)
        Log.d("종건", "getNotiList: ${result.data}")
        _notiList.postValue(result.data)
    }

    fun readNoti(noti_id : Long, user_id: Long)=viewModelScope.launch(Dispatchers.IO) {
        Log.d("종건asd", "readNoti: $noti_id , ${user_id}")
        val result = putNotiUseCase.execute(noti_id)
        delay(500)
        val response = getNotiListUseCase.execute(user_id);
        Log.d("종건asd", "readNoti: ${result.data}")
        Log.d("종건asd","readNoti : ${response.data}")
        _notiList.postValue(response.data)
    }


}