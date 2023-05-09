package com.team.parking.presentation.di

import com.team.parking.data.api.*
import com.team.parking.data.repository.dataSource.*
import com.team.parking.data.repository.dataSourceImpl.*
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
}