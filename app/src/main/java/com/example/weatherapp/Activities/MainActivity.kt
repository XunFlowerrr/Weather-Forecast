package com.example.weatherapp.Activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.weatherapp.Adapters.HourlyAdapters
import com.example.weatherapp.R
import com.example.weatherapp.Domains.Hourly
import kotlin.collections.ArrayList

class MainActivity : ComponentActivity() {
    private lateinit var adapterHourly: HourlyAdapters
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize adapter and recyclerView here
        recyclerView = findViewById(R.id.recyclerView_1)
        adapterHourly = HourlyAdapters()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val itemsinit: ArrayList<Hourly> = ArrayList()
        itemsinit.add(Hourly("12:00", 12, "sunny"))
        itemsinit.add(Hourly("13:00", 13, "sunny"))
        itemsinit.add(Hourly("14:00", 14, "sunny"))
        itemsinit.add(Hourly("15:00", 15, "sunny"))
        itemsinit.add(Hourly("16:00", 16, "sunny"))

        adapterHourly.items = itemsinit

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapterHourly
    }
}
