package com.example.weatherapp.domains

import androidx.annotation.DrawableRes
import com.example.weatherapp.R

data class Hourly(
    val hour: String = "",
    val temp: Int = 0,
    val status: String = "",
    @DrawableRes val picResId: Int = R.drawable.cloudy_sunny,
    val time: String = ""
)