package com.team.parking.presentation.utils

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import com.team.parking.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_KEY)

    }
}
