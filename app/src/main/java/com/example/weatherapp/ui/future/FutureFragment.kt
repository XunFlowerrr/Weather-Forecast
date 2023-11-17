package com.example.weatherapp.ui.future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domains.FutureDomain

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

        view.findViewById<RecyclerView>(R.id.recyclerView_2)?.apply {
            layoutManager = LinearLayoutManager(
                this@FutureFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )

            adapter = FutureAdapter(
                items = listOf(
                    FutureDomain(
                        day = "Mon",
                        picResId = R.drawable.sunny,
                        status = "Sunny",
                        highTemp = 20,
                        lowTemp = 12
                    ),
                    FutureDomain(
                        day = "Tue",
                        picResId = R.drawable.cloudy_sunny,
                        status = "Cloudy Sunny",
                        highTemp = 22,
                        lowTemp = 15
                    ),
                    FutureDomain(
                        day = "Wed",
                        picResId = R.drawable.snowy,
                        status = "Sunny",
                        highTemp = 21,
                        lowTemp = 13
                    ),
                    FutureDomain(
                        day = "Thu",
                        picResId = R.drawable.snowy,
                        status = "Sunny",
                        highTemp = 20,
                        lowTemp = 12
                    ),
                    FutureDomain(
                        day = "Fri",
                        picResId = R.drawable.snowy,
                        status = "Sunny",
                        highTemp = 20,
                        lowTemp = 12
                    ),
                    FutureDomain(
                        day = "Sat",
                        picResId = R.drawable.snowy,
                        status = "Sunny",
                        highTemp = 20,
                        lowTemp = 12
                    ),
                )
            )
        }

        view.findViewById<ConstraintLayout>(R.id.backButton)?.apply {
            setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
