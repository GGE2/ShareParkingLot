/*
package com.team.parking.presentation

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.team.parking.R
import java.util.*

class MarkerPool private constructor(resources: Resources,lat:Double,lng: Double){

    private val markerStack: Stack<Marker> = Stack()

    companion object{


    }

    fun getMarker() : Marker{
        return if(markerStack.isEmpty()){
            createNewMarker()
        }

    }

    private fun createNewMarker(resources: Resources,lat:Double,lng:Double){
        val marker = Marker()
        val markerBitmap = decodeSampledBitmapFromResource(resources, R.drawable.ba,130,130)
        val icon = OverlayImage.fromBitmap(markerBitmap)
        marker.width = 130
        marker.height = 130
        marker.icon = icon
        markerStack.push(marker)
    }


    */
/**
     * 이미지 크기 구하기
     *//*


    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
    */
/**
     * 최적화 이미지 반환
     *//*

    private fun decodeSampledBitmapFromResource(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, this)

            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false

            BitmapFactory.decodeResource(res, resId, this)
        }
    }

}*/
