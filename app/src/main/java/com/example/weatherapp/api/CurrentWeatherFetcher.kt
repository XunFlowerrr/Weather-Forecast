package com.example.weatherapp.api

import android.util.Log
import com.example.weatherapp.domains.CurrentWeatherDomain
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class CurrentWeatherFetcher(private val apiKey: String) {
    private val client = OkHttpClient()

    fun fetchCurrentWeather(query: String, callback: (String?) -> Unit) {
        val url = "http://api.weatherapi.com/v1/current.json?key=$apiKey&q=$query"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(
                call: okhttp3.Call, response: okhttp3.Response
            ) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody = response.body?.string()
                    callback(responseBody)
                }
            }
        })
    }

    fun parseCurrentWeather(responseBody: String): CurrentWeatherDomain {
        val jsonObj = JSONObject(responseBody)
        val currentWeatherDomain = CurrentWeatherDomain()

        // Extracting location information
        val locationObj = jsonObj.getJSONObject("location")
        val locationName = locationObj.getString("name")
//        val locationRegion = locationObj.getString("region")
        val locationCountry = locationObj.getString("country")

        // Extracting current weather information
        val currentObj = jsonObj.getJSONObject("current")
        val currentStatus = currentObj.getJSONObject("condition").getString("text")
        val currentTemp = currentObj.getDouble("temp_c")
        val currentDate = currentObj.getString("last_updated").substring(0, 10)
        val currentTime = currentObj.getString("last_updated").substring(11, 16)
        val currentRain = currentObj.getDouble("precip_mm")
        val currentWindSpeed = currentObj.getDouble("wind_kph")
        val currentHumidity = currentObj.getInt("humidity")
        val currentLocation = "$locationName | $locationCountry"

        // Updating the CurrentWeatherDomain object
        currentWeatherDomain.currentStatus = currentStatus
        currentWeatherDomain.currentTemp = currentTemp
        currentWeatherDomain.currentDate = currentDate
        currentWeatherDomain.currentTime = currentTime
        currentWeatherDomain.currentRain = currentRain
        currentWeatherDomain.currentWindSpeed = currentWindSpeed
        currentWeatherDomain.currentHumidity = currentHumidity
        currentWeatherDomain.currentLocation = currentLocation

        Log.d("customAPI", "parseCurrentWeather: $currentWeatherDomain")

        return currentWeatherDomain
    }
}