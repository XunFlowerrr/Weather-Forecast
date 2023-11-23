package com.example.weatherapp.domains

import androidx.annotation.DrawableRes

data class FutureDomain(
    val day: String = "",
    @DrawableRes
    val picResId: Int = 0,
    val status: String = "",
    val highTemp: Double = 0.0,
    val lowTemp: Double = 0.0,
    val rainD: Double = 0.0,
    val humidityD: Double = 0.0,
    val windD: Double = 0.0
)