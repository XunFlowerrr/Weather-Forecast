package com.example.weatherapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapters.FutureAdapter
import com.example.weatherapp.domains.FutureDomain


class FutureActivity : AppCompatActivity() {
    private lateinit var adapterFuture: FutureAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_future)

        initRecyclerView()
        setVariable()
    }

    private fun setVariable() {
        val backButton: ConstraintLayout = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_2)
        adapterFuture = FutureAdapter(
            listOf(
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

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapterFuture
    }
}


