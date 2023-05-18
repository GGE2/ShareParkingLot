package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.data.model.map.*
import com.team.parking.data.util.Resource
import com.team.parking.domain.usecase.GetSearchAddressUseCase
import com.team.parking.domain.usecase.GetSearchDataUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchAddressViewModel(
    private val app : Application,
    val searchAddressUseCase: GetSearchAddressUseCase
    ) : AndroidViewModel(app){

    val application = App()

    private var _searchAddress : MutableLiveData<Resource<SearchAddressResponse>> = MutableLiveData()
    val searchAddress : LiveData<Resource<SearchAddressResponse>> get() = _searchAddress

    //검색어
    var query : MutableLiveData<String> = MutableLiveData()
    //검색후 선택된 Place
    var searchedAddress : MutableLiveData<AddressResponse?> = MutableLiveData()

    fun resultClear(){
        searchedAddress.value = null
    }

    /**
     * 카카오 주소 검사
     */

    fun getAddressByKeyword(apiKey:String,query:String) = viewModelScope.launch(Dispatchers.IO){
        _searchAddress.postValue(Resource.Loading())
        try{
            if(application.isNetworkAvailable(app)){
                val apiResult = searchAddressUseCase.execute(apiKey, query)
                _searchAddress.postValue(apiResult)
            }else{
                _searchAddress.postValue(Resource.Error("인터넷 사용이 불가능합니다."))
            }

        }catch(e:Exception){
            _searchAddress.postValue(Resource.Error(e.message.toString()))
        }
    }
}