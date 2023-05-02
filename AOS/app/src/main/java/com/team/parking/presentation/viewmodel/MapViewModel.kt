package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.naver.maps.map.overlay.Marker
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.usecase.GetMapDataUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MapViewModel_지훈"
class MapViewModel(
    private val app:Application,
    val getMapDataUseCase: GetMapDataUseCase
) : AndroidViewModel(app){
    val application = App()


    //지도 주차장 데이터
    private var _parkingLots : MutableLiveData<Resource<List<MapResponse>>> = MutableLiveData()
    val parkingLots : LiveData<Resource<List<MapResponse>>> get() = _parkingLots

    private var _marker : MutableLiveData<Marker> = MutableLiveData()

    /**
     * 서버로부터 주차장 데이터 받기
     */
    fun getMapDatas(mapRequest: MapRequest) = viewModelScope.launch(Dispatchers.IO) {
        _parkingLots.postValue(Resource.Loading())
        try{
            if(application.isNetworkAvailable(app)){
                val apiResult =getMapDataUseCase.execute(mapRequest)
                _parkingLots.postValue(apiResult)
            }else{
                _parkingLots.postValue(Resource.Error("인터넷 사용이 불가능합니다."))
            }

        }catch (e:Exception){
            _parkingLots.postValue(Resource.Error(e.message.toString()))
        }

    }


}