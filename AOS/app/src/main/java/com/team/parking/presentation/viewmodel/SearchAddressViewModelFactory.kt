package com.team.parking.presentation.viewmodel

import android.app.Application
import android.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.GetSearchAddressUseCase
import com.team.parking.domain.usecase.GetSearchDataUseCase

class SearchAddressViewModelFactory(
    private val app : Application,
    val getSearchAddressUseCase: GetSearchAddressUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchAddressViewModel(app,getSearchAddressUseCase) as T
    }
}