package com.team.parking.presentation.di

import com.team.parking.data.api.MapAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun provideMapRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://k8d108.p.ssafy.io:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMapService(retrofit:Retrofit):MapAPIService{
        return retrofit.create(MapAPIService::class.java)
    }

}