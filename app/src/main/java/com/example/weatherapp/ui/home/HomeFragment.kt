package com.example.weatherapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domains.Hourly

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
    }
}