package com.team.parking.presentation.di

import android.app.Application
import com.team.parking.domain.usecase.*

import com.team.parking.presentation.viewmodel.MapViewModelFactory
import com.team.parking.presentation.viewmodel.SearchViewModelFactory
import com.team.parking.presentation.viewmodel.UserViewModelFactory


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
        app:Application,getMapDataUseCase: GetMapDataUseCase,
        getMapDetailDataUseCase: GetMapDetailDataUseCase,
        getMapOrderByDistanceDataUseCase: GetParkingOrderByDistanceDataUseCase,
        getMapOrderByPriceDataUseCase: GetParkingOrderByPriceDataUseCase,
        getSelectedShareLotUseCase: GetSelectedShareLotUseCase,
        selectedShareLotUseCase: GetSelectedShareLotUseCase
    ):MapViewModelFactory{
        return MapViewModelFactory(app,getMapDataUseCase
            ,getMapDetailDataUseCase,getMapOrderByDistanceDataUseCase, getMapOrderByPriceDataUseCase,selectedShareLotUseCase)
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
    fun provideUserViewModelFactory(
        app:Application,
        getUserUseCase: GetUserUseCase,
        postUserUseCase: PostUserUseCase,
        putFcmTokenUseCase : PutFcmTokenUseCase,
        postAuthMessageUseCase: PostAuthMessageUseCase,
        getAuthMessageUseCase: GetAuthMessageUseCase,
        getEmailUseCase: GetEmailUseCase,
        putProfileImageUseCase: PutProfileImageUseCase,
        getUserInfoUseCase: GetUserInfoUseCase
        ) : UserViewModelFactory {
        return UserViewModelFactory(
            app, getUserUseCase,
            getEmailUseCase,
            postAuthMessageUseCase,
            postUserUseCase,
            putFcmTokenUseCase,
            getAuthMessageUseCase,
            putProfileImageUseCase,
            getUserInfoUseCase
        )
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

    @Singleton
    @Provides
    fun providePointViewModelFactory(
        app:Application,
        getCurrentPointUseCase: GetCurrentPointUseCase,
        putChargePointUseCase: PutChargePointUseCase
    ) : PointViewModelFactory{
        return PointViewModelFactory(app, getCurrentPointUseCase, putChargePointUseCase)
    }

    @Singleton
    @Provides
    fun provideTransactionHistoryViewModelFactory(
        app:Application,
        getEarnedPointUseCase: GetEarnedPointUseCase,
        getSpentPointUseCase: GetSpentPointUseCase
    ) : TransactionHistoryViewModelFactory{
        return TransactionHistoryViewModelFactory(app, getEarnedPointUseCase, getSpentPointUseCase)
    }

    @Singleton
    @Provides
    fun provideCarViewModelFactory(
        app:Application,
        setRepCarUseCase: SetRepCarUseCase,
        getCarListUseCase: GetCarListUseCase,
        postCarUseCase: PostCarUseCase
    ) : CarViewModelFactory{
        return CarViewModelFactory(app, setRepCarUseCase, getCarListUseCase, postCarUseCase)
    }

    @Singleton
    @Provides
    fun providePurchaseTicketViewModelFactory(
        app:Application,
        getTicketAvailableUseCase: GetTicketAvailableUseCase,
        postPurchaseTicketUseCase: PostPurchaseTicketUseCase
    ) : PurchaseTicketViewModelFactory{
        return PurchaseTicketViewModelFactory(app, getTicketAvailableUseCase, postPurchaseTicketUseCase)
    }

    @Singleton
    @Provides
    fun provideMyTicketViewModelFactory(
        app:Application,
        getTicketBoughtListUseCase: GetTicketBoughtListUseCase,
        getTicketSoldListUseCase: GetTicketSoldListUseCase
    ) : MyTicketViewModelFactory{
        return MyTicketViewModelFactory(app, getTicketBoughtListUseCase, getTicketSoldListUseCase)
    }

    @Singleton
    @Provides
    fun provideTicketDetailViewModelFactory(
        app:Application,
        getTicketDetailUseCase: GetTicketDetailUseCase,
        putTicketBuyConfirmUseCase: PutTicketBuyConfirmUseCase,
        putTicketSellConfirmUseCase: PutTicketSellConfirmUseCase
    ) : TicketDetailViewModelFactory{
        return TicketDetailViewModelFactory(app, getTicketDetailUseCase, putTicketBuyConfirmUseCase, putTicketSellConfirmUseCase)

    }

    @Singleton
    @Provides
    fun provideFavoriteViewModelFactory(
        app:Application,
        setFavoriteUseCase: SetFavoriteUseCase,
        getFavoriteListUseCase: GetFavoriteListUseCase
    ) : FavoriteViewModelFactory{
        return FavoriteViewModelFactory(app, setFavoriteUseCase, getFavoriteListUseCase)
    }

    @Singleton
    @Provides
    fun provideNotificationViewModelFactory(
        app:Application,
        getNotiListUseCase: GetNotiListUseCase,
        putNotiUseCase: PutNotiUseCase,
        deleteAllNotiUseCase: DeleteAllNotiUseCase,
    ) : NotificationViewModelFactory{
        return NotificationViewModelFactory(app, getNotiListUseCase, putNotiUseCase,deleteAllNotiUseCase)
    }
}