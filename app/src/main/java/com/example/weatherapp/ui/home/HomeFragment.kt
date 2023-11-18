package com.example.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.api.CurrentLocationFetcher
import com.example.weatherapp.api.CurrentWeatherFetcher
import com.example.weatherapp.domains.CurrentWeatherDomain
import com.example.weatherapp.domains.Hourly
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recyclerView_1).apply {
            layoutManager = LinearLayoutManager(
                this@HomeFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = HourlyAdapter(
                items = (10..23).map {
                    Hourly(
                        hour = "$it:00",
                        temp = 15 - (it / 3),
                        picResId = R.drawable.snowy
                    )
                }
            )
        }

        view.findViewById<View>(R.id.nextButton)?.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_futureFragment)
            }
        }

        requireActivity().apply {
            val currentLocationFetcher = CurrentLocationFetcher(this)
            val currentWeatherFetcher = CurrentWeatherFetcher(BuildConfig.Weather_API_KEY)

            viewLifecycleOwner.lifecycleScope.launch {
                val location = currentLocationFetcher.getLocation()

                withContext(Dispatchers.IO) {
                    val currentWeather =
                        currentWeatherFetcher.fetchCurrentWeather("${location.latitude},${location.longitude}")

                    updateCurrentWeather(currentWeather)
                }
            }
        }
    }

    private fun updateCurrentWeather(currentWeatherDomain: CurrentWeatherDomain) {
        Log.d("CurrentWeather", currentWeatherDomain.toString())
    }
}