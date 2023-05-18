package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.*
import com.team.parking.presentation.utils.App

class FavoriteViewModelFactory(
    private val app:Application,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase
):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(
            app,
            setFavoriteUseCase,
            getFavoriteListUseCase
        ) as T
    }
}