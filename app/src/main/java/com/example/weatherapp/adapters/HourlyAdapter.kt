package com.example.weatherapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.domains.Hourly

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    var items: ArrayList<Hourly> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hourText: TextView = itemView.findViewById(R.id.hourlyText)
        var tempText: TextView = itemView.findViewById(R.id.tempText)

        @SuppressLint("DiscouragedApi")
        fun setPicPath(picPath: String) {
            val drawableResourceId: Int = itemView.resources.getIdentifier(
                picPath,
                "drawable",
                itemView.context.packageName
            )

            Glide.with(itemView.context).load(drawableResourceId)
                .into(itemView.findViewById(R.id.pic))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_hourly, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            holder.hourText.text = hour
            holder.tempText.text = "${temp}C"
            holder.setPicPath(picPath)
        }
    }
}