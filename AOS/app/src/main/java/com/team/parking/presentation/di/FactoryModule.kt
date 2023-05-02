package com.team.parking.presentation.di

import android.app.Application
import com.team.parking.domain.usecase.GetMapDataUseCase
import com.team.parking.domain.usecase.GetSearchDataUseCase
import com.team.parking.presentation.viewmodel.MapViewModelFactory
import com.team.parking.presentation.viewmodel.SearchViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideMapViewModelFactory(
        app:Application,getMapDataUseCase: GetMapDataUseCase
    ):MapViewModelFactory{
        return MapViewModelFactory(app,getMapDataUseCase)
    }


    @Singleton
    @Provides
    fun provideSearchViewModelFactory(
        app:Application,searchDataUseCase: GetSearchDataUseCase
    ) : SearchViewModelFactory{
        return SearchViewModelFactory(app,searchDataUseCase)
    }

}