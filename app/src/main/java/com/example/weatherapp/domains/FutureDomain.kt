package com.example.weatherapp.domains
data class FutureDomain(
    var day: String = "",
    var picPath: String = "",
    var status: String = "",
    var highTemp: Int = 0,
    var lowTemp: Int = 0
)