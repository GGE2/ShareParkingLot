package com.team.parking.presentation.utils

import android.app.Application
import android.os.Build
import com.kakao.sdk.common.KakaoSdk
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.naver.maps.map.NaverMapSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.team.parking.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class App : Application(){
    companion object{
        lateinit var retrofit: Retrofit
        lateinit var userRetrofit: Retrofit
    }
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_KEY)
        KakaoSdk.init(this,BuildConfig.KAKAO_CLIENT_KEY)
        NaverIdLoginSDK.initialize(this, "${BuildConfig.NAVER_CLIENT_ID}", "${BuildConfig.NAVER_CLIENT_SECRET}" , "네이버 로그인")
        KakaoSdk.init(this, "${BuildConfig.KAKAO_CLIENT_KEY}")
        userRetrofit= Retrofit.Builder()
            .baseUrl("http://k8d108.p.ssafy.io:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl("http://k8d108.p.ssafy.io:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    /**
     * 네트워크 사용가능 검사
     */

    fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true

                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }
}
