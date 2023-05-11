package com.team.parking.data.model.map


import com.google.gson.annotations.SerializedName

data class MapDetailResponse(
    @SerializedName("dayFee")
    val dayFee: Int,
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("feeBasic")
    val feeBasic: Int,
    @SerializedName("feeData")
    val feeData: String,
    @SerializedName("imageUrl")
    val imageUrl: List<String>,
    @SerializedName("jibun")
    val jibun: String,
    @SerializedName("lotName")
    val lotName: String,
    @SerializedName("lotType")
    val lotType: String,
    @SerializedName("openDay")
    val openDay: String,
    @SerializedName("parkId")
    val parkId: Int,
    @SerializedName("payType")
    val payType: String,
    @SerializedName("roadAddr")
    val roadAddr: String,
    @SerializedName("specialProp")
    val specialProp: String
)