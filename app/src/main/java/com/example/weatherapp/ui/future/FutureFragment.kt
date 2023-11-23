package com.example.weatherapp.ui.future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.api.CurrentLocationFetcher
import com.example.weatherapp.api.FutureWeatherFetcher
import com.example.weatherapp.domains.FutureDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FutureFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_future, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireActivity())
        {
            val futureWeatherFetcher = FutureWeatherFetcher(this, BuildConfig.Weather_API_KEY)
            val currentLocationFetcher = CurrentLocationFetcher(this)

            viewLifecycleOwner.lifecycleScope.launch {
                val location = currentLocationFetcher.getLocation()
                withContext(Dispatchers.IO)
                {
                    val futureWeather =
                        futureWeatherFetcher.fetchFutureWeather("${location.latitude},${location.longitude}")
                    updateFutureWeather(futureWeather)
                }
            }
        }

        view.findViewById<View>(R.id.backButton)?.apply {
            setOnClickListener {
                findNavController().popBackStack()
            }
        }


    }

    private fun updateFutureWeather(futureWeather: List<FutureDomain>) {
        requireActivity().runOnUiThread {


            view?.findViewById<RecyclerView>(R.id.recyclerView_2)?.apply {
                layoutManager = LinearLayoutManager(
                    this@FutureFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = FutureAdapter(futureWeather)
            }

            val tomorrow = futureWeather[0]
            val tomorrowTempView = view?.findViewById<TextView>(R.id.tomorrowTemp)
            "${tomorrow.highTemp} °C".also { tomorrowTempView?.text = it }


            val tomorrowStatusView = view?.findViewById<TextView>(R.id.tomorrowStatus)
            tomorrowStatusView?.text = tomorrow.status

            val tomorrowRain = view?.findViewById<TextView>(R.id.tomorrowRain)
            "${tomorrow.rainD} %".also { tomorrowRain?.text = it }
            tomorrowRain?.visibility = View.VISIBLE

            val tomorrowHumidity = view?.findViewById<TextView>(R.id.tomorrowHumidity)
            "${tomorrow.humidityD} %".also { tomorrowHumidity?.text = it }
            tomorrowHumidity?.visibility = View.VISIBLE

            val tomorrowWind = view?.findViewById<TextView>(R.id.tomorrowWind)
            "${tomorrow.windD} km/h".also { tomorrowWind?.text = it }
            tomorrowWind?.visibility = View.VISIBLE

            val tomorrowPic = view?.findViewById<ImageView>(R.id.tomorrowImg)
            tomorrowPic.let {
                if (tomorrowPic != null) {
                    Glide.with(this@FutureFragment).load(tomorrow.picResId)
                        .into(tomorrowPic)
                }
            }

            val progressBarTomorrow = view?.findViewById<View>(R.id.futureProgressBar)
            progressBarTomorrow?.visibility = View.GONE

            val linearLayoutTomorrow = view?.findViewById<View>(R.id.linear_layout_1)
            linearLayoutTomorrow?.visibility = View.VISIBLE

        }
    }
}
