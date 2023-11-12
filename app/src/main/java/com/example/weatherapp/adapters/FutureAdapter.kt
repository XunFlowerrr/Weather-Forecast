package com.example.weatherapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.domains.FutureDomain

class FutureAdapter : RecyclerView.Adapter<FutureAdapter.ViewHolder>() {

    var items: ArrayList<FutureDomain> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dayText: TextView = itemView.findViewById(R.id.dayText)
//        var picPath: ImageView = itemView.findViewById(R.id.picFuture)
        var status: TextView = itemView.findViewById(R.id.statusText)
        var lowText: TextView = itemView.findViewById(R.id.lowText)
        var highText: TextView = itemView.findViewById(R.id.highText)

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
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_future, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            holder.dayText.text = day
            holder.status.text = status
            holder.lowText.text = "${lowTemp}C"
            holder.highText.text = "${highTemp}C"
            holder.setPicPath(picPath)
        }
    }
}