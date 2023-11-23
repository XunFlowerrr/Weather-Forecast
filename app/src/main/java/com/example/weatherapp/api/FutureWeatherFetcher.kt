package com.example.weatherapp.api

import android.content.Context
import com.example.weatherapp.domains.FutureDomain
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class FutureWeatherFetcher(private val context: Context, private val apiKey: String) {
    private val client = OkHttpClient()

    suspend fun fetchFutureWeather(query: String): List<FutureDomain> {
        val url = "http://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$query&days=7"
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).await()

        response.use {
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val responseBody = response.body?.string() ?: throw IOException("body is empty")
            return parseFutureWeather(responseBody)
        }
    }

    private fun parseFutureWeather(responseBody: String): List<FutureDomain> {
        val jsonObj = JSONObject(responseBody)
        val forecastArray = jsonObj.getJSONObject("forecast").getJSONArray("forecastday")

        val futureList = mutableListOf<FutureDomain>()

        for (i in 1 until forecastArray.length()) {
            val forecastObj = forecastArray.getJSONObject(i)
            val date = forecastObj.getString("date")
            val day = date.substring(5, 10)
            val lowTemp = forecastObj.getJSONObject("day").getDouble("mintemp_c")
            val highTemp = forecastObj.getJSONObject("day").getDouble("maxtemp_c")
            var status =
                forecastObj.getJSONObject("day").getJSONObject("condition").getString("icon")
            val statusText =
                forecastObj.getJSONObject("day").getJSONObject("condition").getString("text")
            status = status.substringAfterLast("/")

            // find number in icon string
            val regex = Regex("[0-9]+")
            val statusNum = regex.find(status)?.value?.toInt()

            // find icon resource name
            val statusResource = "day_$statusNum"
            val statusId =
                context.resources.getIdentifier(statusResource, "drawable", context.packageName)

            val rain = forecastObj.getJSONObject("day").getDouble("daily_chance_of_rain")
            val humidity = forecastObj.getJSONObject("day").getDouble("avghumidity")
            val wind = forecastObj.getJSONObject("day").getDouble("maxwind_kph")

            val future = FutureDomain(
                day = day,
                picResId = statusId,
                status = statusText,
                lowTemp = lowTemp,
                highTemp = highTemp,
                rainD = rain,
                humidityD = humidity,
                windD = wind

            )

            futureList.add(future)


        }
        return futureList
    }
}

