package com.example.weatherapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.domains.Hourly

class HourlyAdapter(
    private val items: List<Hourly> = listOf()
) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourText: TextView = itemView.findViewById(R.id.hourlyText)
        private val tempText: TextView = itemView.findViewById(R.id.tempText)
        private val picView: ImageView = itemView.findViewById(R.id.pic)

        fun bind(hourly: Hourly) {
            hourText.text = hourly.hour
            tempText.text = itemView.resources.getString(R.string.temp_celsius, hourly.temp)
            Glide.with(itemView.context).load(hourly.picResId)
                .into(picView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_hourly, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}