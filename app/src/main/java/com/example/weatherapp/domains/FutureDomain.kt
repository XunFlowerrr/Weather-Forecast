package com.example.weatherapp.domains

import androidx.annotation.DrawableRes

data class FutureDomain(
    val day: String = "",
    @DrawableRes
    val picResId: Int = 0,
    val status: String = "",
    val highTemp: Int = 0,
    val lowTemp: Int = 0
)