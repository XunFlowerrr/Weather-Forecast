package com.example.weatherapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.api.CurrentForecastFetcher
import com.example.weatherapp.api.CurrentLocationFetcher
import com.example.weatherapp.api.CurrentWeatherFetcher
import com.example.weatherapp.domains.CurrentWeatherDomain
import com.example.weatherapp.domains.Hourly
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<View>(R.id.nextButton)?.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_futureFragment)
            }
        }

        with(requireActivity()) {
            val currentLocationFetcher = CurrentLocationFetcher(this)
            val currentWeatherFetcher = CurrentWeatherFetcher(BuildConfig.Weather_API_KEY)
            val currentForecastFetcher = CurrentForecastFetcher(this,BuildConfig.Weather_API_KEY)

            viewLifecycleOwner.lifecycleScope.launch {
                val location = currentLocationFetcher.getLocation()

                withContext(Dispatchers.IO) {
                    val currentWeather =
                        currentWeatherFetcher.fetchCurrentWeather("${location.latitude},${location.longitude}")
                    val currentForecast =
                        currentForecastFetcher.fetchCurrentForecast("${location.latitude},${location.longitude}")

                    updateCurrentWeather(currentWeather)
                    updateCurrentForecast(currentForecast)
                }
            }
        }
    }

    private fun updateCurrentForecast(currentForecast: List<Hourly>) {
        requireActivity().runOnUiThread {
            with(requireView()) {
                val hourlyAdapter = HourlyAdapter(currentForecast)
                findViewById<RecyclerView>(R.id.recyclerView_1).apply {
                    layoutManager = LinearLayoutManager(
                        this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false
                    )

                    adapter = hourlyAdapter
                }
            }
        }

    }

    private fun updateCurrentWeather(currentWeatherDomain: CurrentWeatherDomain) {
        requireActivity().runOnUiThread {
            with(requireView()) {

                val currentStatus: TextView = findViewById(R.id.currentStatusText)
                currentStatus.text = currentWeatherDomain.currentStatus
                currentStatus.visibility = View.VISIBLE

                val currentStatusImg = findViewById<View>(R.id.currentStatusImg)
                currentStatusImg.visibility = View.VISIBLE

                val homeProgressBar = findViewById<View>(R.id.homeProgressBar)
                homeProgressBar.visibility = View.GONE

                val currentTemp: TextView = findViewById(R.id.currentTempText)
                currentTemp.text = currentWeatherDomain.currentTemp.toString()
                currentTemp.visibility = View.VISIBLE

                val currentDate: TextView = findViewById(R.id.currentDateAndTimeText)
                currentDate.text = currentWeatherDomain.dateTime()
                currentDate.visibility = View.VISIBLE

                val currentRain: TextView = findViewById(R.id.currentRainText)
                currentRain.text = resources.getString(
                    R.string.template_percent, (currentWeatherDomain.currentRain * 100).toInt()
                )
                currentRain.visibility = View.VISIBLE

                val currentWindSpeed: TextView = findViewById(R.id.currentWindSpeedText)
                currentWindSpeed.text = resources.getString(
                    R.string.template_killometer_per_hour, currentWeatherDomain.currentWindSpeed
                )
                currentWindSpeed.visibility = View.VISIBLE

                val currentHumidity: TextView = findViewById(R.id.currentHumidityText)
                currentHumidity.text = resources.getString(
                    R.string.template_percent, currentWeatherDomain.currentHumidity
                )
                currentHumidity.visibility = View.VISIBLE

                val currentLocation: TextView = findViewById(R.id.currentLocationText)
                currentLocation.text = currentWeatherDomain.currentLocation
                currentLocation.visibility = View.VISIBLE
            }
        }
    }


}


fun CurrentWeatherDomain.dateTime(): String {
    return "$currentDate $currentTime"
}