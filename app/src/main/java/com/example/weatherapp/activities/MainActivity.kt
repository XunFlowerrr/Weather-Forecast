package com.example.weatherapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.HourlyAdapter
import com.example.weatherapp.R
import com.example.weatherapp.domains.Hourly
import kotlin.collections.ArrayList

class MainActivity : ComponentActivity() {
    private lateinit var adapterHourly: HourlyAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize adapter and recyclerView here
        recyclerView = findViewById(R.id.recyclerView_1)
        adapterHourly = HourlyAdapter()

        initRecyclerView()
        setVariable()
    }

    private fun setVariable() {
        val nextButton : TextView = findViewById(R.id.nextButton)
        nextButton.setOnClickListener{view ->
            val intent = Intent(this, FutureActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        val itemListInit: ArrayList<Hourly> = ArrayList()
        itemListInit.add(Hourly("12:00", 12, "snowy"))
        itemListInit.add(Hourly("13:00", 13, "storm"))
        itemListInit.add(Hourly("14:00", 14, "sunny"))
        itemListInit.add(Hourly("15:00", 15, "windy"))
        itemListInit.add(Hourly("16:00", 16, "rainy"))

        adapterHourly.items = itemListInit

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapterHourly
    }
}
