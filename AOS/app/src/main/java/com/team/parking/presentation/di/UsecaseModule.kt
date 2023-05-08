package com.team.parking.presentation.di

import com.team.parking.domain.repository.MapRepository
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
    fun provideGetSearchDataUsecase(
        searchRepository: SearchRepository
    ) : GetSearchDataUseCase{
        return GetSearchDataUseCase(searchRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchAddressUsecase(
        searchRepository: SearchRepository
    ) : GetSearchAddressUseCase{
        return GetSearchAddressUseCase(searchRepository)
    }

    @Singleton
    @Provides
    fun providePostShareLotUsecase(
        shareLotRepository: ShareLotRepository
    ) : PostShareLotUseCase{
        return PostShareLotUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideGetShareLotListUsecase(
        shareLotRepository: ShareLotRepository
    ) : GetShareLotListUseCase{
        return GetShareLotListUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideGetShareLotDayUsecase(
        shareLotRepository: ShareLotRepository
    ) : GetShareLotDayUseCase{
        return GetShareLotDayUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun providePutShareLotDayUsecase(
        shareLotRepository: ShareLotRepository
    ) : PutShareLotDayUseCase{
        return PutShareLotDayUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteShareLotUsecase(
        shareLotRepository: ShareLotRepository
    ) : DeleteShareLotUseCase{
        return DeleteShareLotUseCase(shareLotRepository)
    }
    
}