package com.team.parking.presentation.di

import android.app.Application
import com.team.parking.domain.usecase.*
import com.team.parking.presentation.viewmodel.*
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
        app:Application,getMapDataUseCase: GetMapDataUseCase,getMapDetailDataUseCase: GetMapDetailDataUseCase
    ):MapViewModelFactory{
        return MapViewModelFactory(app,getMapDataUseCase,getMapDetailDataUseCase)
    }


    @Singleton
    @Provides
    fun provideSearchViewModelFactory(
        app:Application,searchDataUseCase: GetSearchDataUseCase
    ) : SearchViewModelFactory{
        return SearchViewModelFactory(app,searchDataUseCase)
    }

    @Singleton
    @Provides
    fun provideSearchAddressViewModelFactory(
        app:Application,searchAddressUseCase: GetSearchAddressUseCase
    ) : SearchAddressViewModelFactory{
        return SearchAddressViewModelFactory(app,searchAddressUseCase)
    }

    @Singleton
    @Provides
    fun provideShareParkingLotViewModelFactory(
        app:Application, postShareLotUseCase: PostShareLotUseCase, getShareLotListUseCase: GetShareLotListUseCase,
        deleteShareLotUseCase: DeleteShareLotUseCase
    ) : ShareParkingLotViewModelFactory{
        return ShareParkingLotViewModelFactory(app, postShareLotUseCase, getShareLotListUseCase, deleteShareLotUseCase)
    }

    @Singleton
    @Provides
    fun provideDaySelectViewModelFactory(
        app:Application,
        getShareLotDayUseCase: GetShareLotDayUseCase,
        putShareLotDayUseCase: PutShareLotDayUseCase
    ) : DaySelectViewModelFactory{
        return DaySelectViewModelFactory(app, getShareLotDayUseCase, putShareLotDayUseCase)
    }
}