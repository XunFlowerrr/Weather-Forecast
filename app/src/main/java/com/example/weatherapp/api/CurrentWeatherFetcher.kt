package com.example.weatherapp.api

import android.content.Context
import android.util.Log
import com.example.weatherapp.domains.CurrentWeatherDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resumeWithException


class CurrentWeatherFetcher(private val client: OkHttpClient ,private val context: Context, private val apiKey: String) {

    suspend fun fetchCurrentWeather(query: String): CurrentWeatherDomain {
        val url = "http://api.weatherapi.com/v1/current.json?key=$apiKey&q=$query"
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).await()

        response.use {
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val responseBody = response.body?.string() ?: throw IOException("body is empty")

            return parseCurrentWeather(responseBody)
        }
    }

    private fun parseCurrentWeather(responseBody: String): CurrentWeatherDomain {
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

        val isDay = currentObj.getInt("is_day")
        var currentStatusId = currentObj.getJSONObject("condition").getString("icon")
        currentStatusId = currentStatusId.substringAfterLast("/")
        val regex = Regex("[0-9]+")
        val currentStatusNum = regex.find(currentStatusId)?.value?.toInt()
        val currentStatusResource = if (isDay == 1) {
            "day_$currentStatusNum"
        } else {
            "night_$currentStatusNum"
        }

        context.resources.getIdentifier(currentStatusResource, "drawable", context.packageName)
            .let {
                currentWeatherDomain.currentPicResId = it
            }


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

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun Call.await(): Response = suspendCancellableCoroutine { continuation ->
    continuation.invokeOnCancellation {
        cancel()
    }

    enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            continuation.resumeWithException(e)
        }

        override fun onResponse(call: Call, response: Response) {
            continuation.resume(value = response, onCancellation = { call.cancel() })
        }
    })
}