package com.simma.simmaapp.model.advertismentResponse

data class Result(
    val __v: Int,
    val _id: String,
    val actionId: String?,
    val actionType: String?,
    val arBannerAd: ArBannerAd,
    val arBannerAdId: String,
    val description: String,
    val enBannerAd: EnBannerAd,
    val enBannerAdId: String,
    val id: String,
    val isClickable: Boolean,
    val order: Int
)