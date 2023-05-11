package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.naver.maps.map.overlay.Marker
import com.navercorp.nid.log.toMessage
import com.team.parking.data.model.map.MapDetailResponse
import com.team.parking.data.model.map.MapOrderResponse
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.model.parkinglot.ParkingLotResponse
import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.usecase.*
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.lang3.mutable.Mutable

private const val TAG = "MapViewModel_지훈"
class MapViewModel(
    private val app:Application,
    val getMapDataUseCase: GetMapDataUseCase,
    val getMapDetailDataUseCase: GetMapDetailDataUseCase,
    val getMapOrderByDistanceDataUseCase: GetParkingOrderByDistanceDataUseCase,
    val getMapOrderByPriceDataUseCase: GetParkingOrderByPriceDataUseCase,
    val getSelectedSharedLotUseCase : GetSelectedShareLotUseCase
) : AndroidViewModel(app){
    val application = App()


    //지도 주차장 데이터
    private var _parkingLots : MutableLiveData<Resource<List<MapResponse>>> = MutableLiveData()
    val parkingLots : LiveData<Resource<List<MapResponse>>> get() = _parkingLots

    //주차장 상세 데이터
    private var _parkingLot : MutableLiveData<Resource<MapDetailResponse>> = MutableLiveData()
    val parkingLot : LiveData<Resource<MapDetailResponse>> get() = _parkingLot

    //주차장 상세 프래그먼트 데이터
    private var _park : MutableLiveData<MapDetailResponse> = MutableLiveData()
    val park : LiveData<MapDetailResponse> get() = _park


    //일반 : 0, 공유 : 1
    private var _selectedPark : MutableLiveData<Int> = MutableLiveData()
    val selectedPark : LiveData<Int> get() = _selectedPark

    //공유 주차장
    private var _sharedPark : MutableLiveData<Resource<MapDetailResponse>> = MutableLiveData()
    val sharedPark : LiveData<Resource<MapDetailResponse>> get() = _sharedPark

    //전체 주차장 거리순 데이터
    private var _parkingLotOrderByDistance : MutableLiveData<Resource<List<MapOrderResponse>>> = MutableLiveData()
    val parkingLotOrderByDistance : LiveData<Resource<List<MapOrderResponse>>> get() = _parkingLotOrderByDistance

    //전체 주차장 가격순 데이터
    private var _parkingLotOrderByPrice : MutableLiveData<Resource<List<MapOrderResponse>>> = MutableLiveData()
    val parkingLotOrderByPrice : LiveData<Resource<List<MapOrderResponse>>> get() = _parkingLotOrderByPrice



    private var _marker : MutableLiveData<Marker> = MutableLiveData()


    /**
     * 현재 선택된 주차장
     */
    fun updateSelectedPark(type: Int){
        _selectedPark.postValue(type)
    }


    /**
     * 주차장 상세 데이터 갱신(DetailFragment)
     */

    fun updatePark(mapDetailResponse: MapDetailResponse){
        _park.postValue(mapDetailResponse)
    }

    /**
     * 선택된 공유 주차장 데이터 받기
     */

    fun getSharedParkingLotDetail(parkId : Long, userId: Long) = viewModelScope.launch {
        _sharedPark.postValue(Resource.Loading())
        try{
            if(application.isNetworkAvailable(app)){
                val apiResult = getSelectedSharedLotUseCase.execute(parkId, userId)
                _sharedPark.postValue(apiResult)
            }else{
                _sharedPark.postValue(Resource.Error("인터넷 사용 불가"))
            }
        }catch(e : Exception){
            _sharedPark.postValue(Resource.Error(e.message.toString()))
        }
    }

    /**
     * 거리순 정렬 데이터 받기
     */
    fun getParkingOrderByDistance(mapRequest: MapRequest) = viewModelScope.launch {
        _parkingLotOrderByDistance.postValue(Resource.Loading())
        try{
            if(application.isNetworkAvailable(app)){
                val apiResult = getMapOrderByDistanceDataUseCase.execute(mapRequest)
                _parkingLotOrderByDistance.postValue(apiResult)
            }else{
                _parkingLotOrderByDistance.postValue(Resource.Error("인터넷 사용이 불가능합니다."))
            }
        }catch(e:Exception){
            _parkingLotOrderByDistance.postValue(Resource.Error(e.message.toString()))
        }

    }

    /**
     * 가격순 정렬 데이터 받기
     */
    fun getParkingOrderByPrice(mapRequest: MapRequest) = viewModelScope.launch {
        _parkingLotOrderByPrice.postValue(Resource.Loading())
        try{
            if(application.isNetworkAvailable(app)){
                val apiResult = getMapOrderByPriceDataUseCase.execute(mapRequest)
                _parkingLotOrderByPrice.postValue(apiResult)
            }else{
                _parkingLotOrderByPrice.postValue(Resource.Error("인터넷 사용이 불가능합니다."))
            }
        }catch(e:Exception){
            _parkingLotOrderByPrice.postValue(Resource.Error(e.message.toString()))
        }
    }


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

    /**
     * 주차장 상세 데이터 받기
     */
    fun getDetailMapData(parkId : Int, userId: Long) = viewModelScope.launch(Dispatchers.IO){
        _parkingLot.postValue(Resource.Loading())
        try{
            if(application.isNetworkAvailable(app)){
                val apiResult = getMapDetailDataUseCase.execute(parkId, userId)
                _parkingLot.postValue(apiResult)
            }else{
                _parkingLot.postValue(Resource.Error("인터넷 사용이 불가능합니다."))
            }

        }catch(e:Exception){
            _parkingLot.postValue(Resource.Error(e.message.toString()))
        }


    }


}