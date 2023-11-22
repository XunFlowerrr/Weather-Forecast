package com.example.weatherapp.api

import android.content.Context
import android.util.Log
import com.example.weatherapp.domains.Hourly
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.util.Calendar

class CurrentForecastFetcher(private val context: Context, private val apiKey: String) {
    private val client = OkHttpClient()

    suspend fun fetchCurrentForecast(query: String): List<Hourly> {
        val url = "http://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$query"
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).await()

        response.use {
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val responseBody = response.body?.string() ?: throw IOException("body is empty")
            return parseCurrentForecast(responseBody)
        }
    }

    private fun parseCurrentForecast(responseBody: String): List<Hourly> {
        val jsonObj = JSONObject(responseBody)
        // Extracting location information
        val hourArray =
            jsonObj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0)
                .getJSONArray("hour")
        val hourList = mutableListOf<Hourly>()
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        for (i in currentHour until hourArray.length()) {
            val hourObj = hourArray.getJSONObject(i)
            val hourTime = hourObj.getString("time").substring(11, 16)
            val hourTemp = hourObj.getDouble("temp_c")
            val isDay = hourObj.getInt("is_day")
            var hourStatus = hourObj.getJSONObject("condition").getString("icon")
            val hourlyStatusText = hourObj.getJSONObject("condition").getString("text")
            hourStatus = hourStatus.substringAfterLast("/")
//
//            find number in icon string
            val regex = Regex("[0-9]+")
            val hourStatusNum = regex.find(hourStatus)?.value?.toInt()

//            find icon resource name
            val hourStatusResource = if (isDay == 1) {
                "day_$hourStatusNum"
            } else {
                "night_$hourStatusNum"
            }

            val hourStatusId =
                context.resources.getIdentifier(hourStatusResource, "drawable", context.packageName)

            val hour = Hourly(
                hour = hourTime,
                temp = hourTemp.toInt(),
                time = hourTime,
                picResId = hourStatusId,
                status = hourlyStatusText
            )
            hourList.add(hour)
            Log.d("CurrentForecastFetcher", "hourList: $hourList")
        }
        return hourList
    }
}

