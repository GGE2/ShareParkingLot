package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.parking.data.model.favorite.FavoriteListResponse
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.util.Resource
import com.team.parking.domain.usecase.GetFavoriteListUseCase
import com.team.parking.domain.usecase.SetFavoriteUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FavoriteViewModel"

class FavoriteViewModel(
    private val app: Application,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase
): AndroidViewModel(app) {
    val application = app as App

    private var _favorite = MutableLiveData<Boolean>(false)
    val favorite: LiveData<Boolean> get() = _favorite

    private var _favoriteList = MutableLiveData<List<FavoriteListResponse>>(listOf())
    val favoriteList: LiveData<List<FavoriteListResponse>> get() = _favoriteList

    fun setFavorite(parkId: Long, parkType: Int, userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        try{
            if(application.isNetworkAvailable(app)){
                val apiResult = setFavoriteUseCase.execute(parkId, parkType, userId)
                _favorite.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }

    fun getFavoriteList(userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        try{
            if(application.isNetworkAvailable(app)){
                val apiResult = getFavoriteListUseCase.execute(userId)
                _favoriteList.postValue(apiResult.data!!)
            }else{
                Log.d(TAG, "get: 네트워크 문제")
            }
        }catch(e:Exception){
            Log.d(TAG, "get: ${e.message}")
        }
    }
}