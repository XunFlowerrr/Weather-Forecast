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
        recyclerView = findViewById(R.id.recyclerView_2)
        adapterFuture = FutureAdapter()

        initRecyclerView()
        setVariable()
    }

    private fun setVariable() {
        val backButton: ConstraintLayout = findViewById(R.id.backButton)

        backButton.setOnClickListener{ view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun initRecyclerView() {
        val itemListInit: ArrayList<FutureDomain> = ArrayList()
        itemListInit.add(FutureDomain("Mon", "snowy", "Sunny", 20, 12))
        itemListInit.add(FutureDomain("Tue", "snowy", "Sunny", 20, 12))
        itemListInit.add(FutureDomain("Wed", "snowy", "Sunny", 20, 12))
        itemListInit.add(FutureDomain("Thu", "snowy", "Sunny", 20, 12))
        itemListInit.add(FutureDomain("Fri", "snowy", "Sunny", 20, 12))
        itemListInit.add(FutureDomain("Sat", "snowy", "Sunny", 20, 12))


        adapterFuture.items = itemListInit


        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapterFuture


    }
}


