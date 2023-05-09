package com.team.parking.presentation.di

import com.team.parking.data.repository.*
import com.team.parking.data.repository.dataSource.*
import com.team.parking.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMapRepository(
        mapRemoteDataSource: MapRemoteDataSource
    ):MapRepository{
        return MapRepositoryImpl(mapRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchRemoteDataSource: SearchRemoteDataSource
    ) : SearchRepository{
        return SearchRepositoryImpl(searchRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideShareLotRepository(
        shareLotRemoteDatasource: ShareLotRemoteDataSource
    ) : ShareLotRepository{
        return ShareLotRepositoryImpl(shareLotRemoteDatasource)
    }

    @Singleton
    @Provides
    fun providePointRepository(
        pointRemoteDataSource: PointRemoteDataSource
    ) : PointRepository{
        return PointRepositoryImpl(pointRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideCarRepository(
        carRemoteDataSource: CarRemoteDataSource
    ) : CarRepository{
        return CarRepositoryImpl(carRemoteDataSource)
    }
}