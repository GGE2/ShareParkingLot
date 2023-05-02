package com.team.parking.presentation.viewmodel

import android.app.Application
import android.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.GetSearchDataUseCase

class SearchViewModelFactory(
    private val app : Application,
    val getSearchDataUseCase: GetSearchDataUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(app,getSearchDataUseCase) as T
    }
}