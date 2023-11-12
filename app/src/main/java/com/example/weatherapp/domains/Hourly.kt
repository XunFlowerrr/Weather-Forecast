package com.example.weatherapp.domains

data class Hourly(
    var hour: String = "",
    var temp: Int = 0,
    var picPath: String = ""
)