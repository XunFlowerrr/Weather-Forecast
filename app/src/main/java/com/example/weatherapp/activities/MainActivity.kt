package com.example.weatherapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.adapters.HourlyAdapter
import com.example.weatherapp.api.CurrentLocationFetcher
import com.example.weatherapp.api.CurrentWeatherFetcher
import com.example.weatherapp.domains.CurrentWeatherDomain
import com.example.weatherapp.domains.Hourly

class MainActivity : ComponentActivity() {
    private lateinit var adapterHourly: HourlyAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        setVariable()

        var currentLocationFetcher: CurrentLocationFetcher = CurrentLocationFetcher(this)
        currentLocationFetcher.fetchLastLocation() { response ->
            if (response != null) {
                Log.d("MainActivity", "$response")

                initCurrentWeatherFetcher(
                    CurrentWeatherFetcher(BuildConfig.Weather_API_KEY),
                    response
                )
            }

        }
    }

    private fun initCurrentWeatherFetcher(
        currentWeatherFetcher: CurrentWeatherFetcher,
        query: String
    ) {
        var currentWeatherDomain: CurrentWeatherDomain
        currentWeatherFetcher.fetchCurrentWeather(query) { response ->
            if (response != null) {

                currentWeatherDomain = currentWeatherFetcher.parseCurrentWeather(response)
                Log.d("MainActivity", "Thread$currentWeatherDomain")

                var currentStatus: TextView = findViewById(R.id.currentStatusText)
                currentStatus.text = currentWeatherDomain.currentStatus

                var currentTemp: TextView = findViewById(R.id.currentTempText)
                currentTemp.text = currentWeatherDomain.currentTemp.toString()

                var currentDate: TextView = findViewById(R.id.currentDateAndTimeText)
                currentDate.text =
                    currentWeatherDomain.currentDate + " " + currentWeatherDomain.currentTime

                var currentRain: TextView = findViewById(R.id.currentRainText)
                currentRain.text = (currentWeatherDomain.currentRain * 100).toString() + " %"

                var currentWindSpeed: TextView = findViewById(R.id.currentWindSpeedText)
                currentWindSpeed.text = currentWeatherDomain.currentWindSpeed.toString() + " km/h"

                var currentHumidity: TextView = findViewById(R.id.currentHumidityText)
                currentHumidity.text = currentWeatherDomain.currentHumidity.toString() + "%"

                var currentLocation: TextView = findViewById(R.id.currentLocationText)
                currentLocation.text = currentWeatherDomain.currentLocation


            }
        }
    }

    private fun setVariable() {
        val nextButton: TextView = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            val intent = Intent(this, FutureActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_1)
        adapterHourly = HourlyAdapter(
            items = (10..23).map {
                Hourly(hour = "$it:00", temp = 15 - (it / 3), picResId = R.drawable.snowy)
            }
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapterHourly
    }
}
