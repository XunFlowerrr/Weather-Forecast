package com.example.weatherapp.domains

import androidx.annotation.DrawableRes

data class Hourly(
    val hour: String = "",
    val temp: Int = 0,
    @DrawableRes
    val picResId: Int = 0
)