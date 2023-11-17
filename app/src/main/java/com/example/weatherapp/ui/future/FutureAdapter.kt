package com.example.weatherapp.ui.future

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.domains.FutureDomain

class FutureAdapter(
    private val items: List<FutureDomain> = listOf()
) : RecyclerView.Adapter<FutureAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayText: TextView = itemView.findViewById(R.id.dayText)
        private val status: TextView = itemView.findViewById(R.id.statusText)
        private val lowText: TextView = itemView.findViewById(R.id.lowText)
        private val highText: TextView = itemView.findViewById(R.id.highText)
        private val picView: ImageView = itemView.findViewById(R.id.picFuture)

        fun bind(future: FutureDomain) {
            dayText.text = future.day
            status.text = future.status
            lowText.text = itemView.resources.getString(R.string.temp_celsius, future.lowTemp)
            highText.text = itemView.resources.getString(R.string.temp_celsius, future.highTemp)
            Glide.with(itemView.context).load(future.picResId)
                .into(picView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_future, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}