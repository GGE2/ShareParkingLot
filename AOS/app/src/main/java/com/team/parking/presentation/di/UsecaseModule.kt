package com.team.parking.presentation.di

import com.team.parking.domain.repository.MapRepository
import com.team.parking.domain.repository.PointRepository
import com.team.parking.domain.repository.SearchRepository
import com.team.parking.domain.repository.ShareLotRepository
import com.team.parking.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class UsecaseModule {

    @Singleton
    @Provides
    fun provideGetMapDataUseCase(
        mapRepository: MapRepository
    ) : GetMapDataUseCase{
        return GetMapDataUseCase(mapRepository)
    }

    @Singleton
    @Provides
    fun provideGetMapDetailDataUseCase(
        mapRepository: MapRepository
    ) : GetMapDetailDataUseCase{
        return GetMapDetailDataUseCase(mapRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchDataUseCase(
        searchRepository: SearchRepository
    ) : GetSearchDataUseCase{
        return GetSearchDataUseCase(searchRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchAddressUseCase(
        searchRepository: SearchRepository
    ) : GetSearchAddressUseCase{
        return GetSearchAddressUseCase(searchRepository)
    }

    @Singleton
    @Provides
    fun providePostShareLotUseCase(
        shareLotRepository: ShareLotRepository
    ) : PostShareLotUseCase{
        return PostShareLotUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideGetShareLotListUseCase(
        shareLotRepository: ShareLotRepository
    ) : GetShareLotListUseCase{
        return GetShareLotListUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideGetShareLotDayUseCase(
        shareLotRepository: ShareLotRepository
    ) : GetShareLotDayUseCase{
        return GetShareLotDayUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun providePutShareLotDayUseCase(
        shareLotRepository: ShareLotRepository
    ) : PutShareLotDayUseCase{
        return PutShareLotDayUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteShareLotUseCase(
        shareLotRepository: ShareLotRepository
    ) : DeleteShareLotUseCase{
        return DeleteShareLotUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrentPointUseCase(
        pointRepository: PointRepository
    ) : GetCurrentPointUseCase{
        return GetCurrentPointUseCase(pointRepository)
    }

    @Singleton
    @Provides
    fun providePutChargePointUseCase(
        pointRepository: PointRepository
    ) : PutChargePointUseCase{
        return PutChargePointUseCase(pointRepository)
    }

    @Singleton
    @Provides
    fun provideGetEarnedPointUseCase(
        pointRepository: PointRepository
    ) : GetEarnedPointUseCase{
        return GetEarnedPointUseCase(pointRepository)
    }

    @Singleton
    @Provides
    fun provideGetSpentPointUseCase(
        pointRepository: PointRepository
    ) : GetSpentPointUseCase{
        return GetSpentPointUseCase(pointRepository)
    }
}