package com.team.parking.presentation.di


import com.team.parking.data.api.MapAPIService
import com.team.parking.data.api.SearchAPIService
import com.team.parking.data.api.UserAPIService
import com.team.parking.data.repository.dataSource.MapRemoteDataSource
import com.team.parking.data.repository.dataSource.SearchRemoteDataSource
import com.team.parking.data.repository.dataSource.UserRemoteDataSource
import com.team.parking.data.repository.localSource.dataSourceImpl.MapRemoteDataSourceImpl
import com.team.parking.data.repository.localSource.dataSourceImpl.SearchRemoteDataSourceImpl
import com.team.parking.data.repository.localSource.dataSourceImpl.UserRemoteDataSourceImpl

import com.team.parking.data.api.*
import com.team.parking.data.repository.dataSource.*
import com.team.parking.data.repository.dataSourceImpl.*
import com.team.parking.data.repository.localSource.dataSourceImpl.NotiRemoteDataSourceImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideMapRemoteDataSource(
        mapAPIService: MapAPIService
    ): MapRemoteDataSource {
        return MapRemoteDataSourceImpl(mapAPIService)
    }

    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(
        searchAPIService: SearchAPIService
    ) : SearchRemoteDataSource{
        return SearchRemoteDataSourceImpl(searchAPIService)
    }

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        userAPIService: UserAPIService
    ) : UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userAPIService)
    }

    @Singleton
    @Provides
    fun provideShareLotRemoteDataSource(
        shareLotAPIService: ShareLotAPIService
    ) : ShareLotRemoteDataSource{
        return ShareLotRemoteDataSourceImpl(shareLotAPIService)
    }

    @Singleton
    @Provides
    fun providePointRemoteDataSource(
        pointAPIService: PointAPIService
    ) : PointRemoteDataSource{
        return PointRemoteDataSourceImpl(pointAPIService)
    }

    @Singleton
    @Provides
    fun provideCarRemoteDataSource(
        carAPIService: CarAPIService
    ) : CarRemoteDataSource{
        return CarRemoteDataSourceImpl(carAPIService)
    }

    @Singleton
    @Provides
    fun provideTicketRemoteDataSource(
        ticketAPIService: TicketAPIService
    ) : TicketRemoteDataSource{
        return TicketRemoteDataSourceImpl(ticketAPIService)
    }

    @Singleton
    @Provides
    fun provideFavoriteRemoteDataSource(
        favoriteAPIService: FavoriteAPIService
    ) : FavoriteRemoteDataSource{
        return FavoriteRemoteDataSourceImpl(favoriteAPIService)

    }

    @Singleton
    @Provides
    fun provideNotiRemoteDataSource(
        notiAPIService: NotiAPIService
    ) : NotiRemoteDataSource{
        return NotiRemoteDataSourceImpl(notiAPIService)
    }
}