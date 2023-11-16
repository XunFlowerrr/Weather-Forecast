package com.example.weatherapp.domains

data class CurrentWeatherDomain(
    var currentStatus: String = "",
    var currentTemp: Double = 0.0,
    var currentDate: String = "",
    var currentTime: String = "",
    var currentRain: Double = 0.0,
    var currentWindSpeed: Double = 0.0,
    var currentHumidity: Int = 0,
    var currentLocation: String = ""

)