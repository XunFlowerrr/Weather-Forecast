package com.example.weatherapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.Domains.Hourly

class HourlyAdapters : RecyclerView.Adapter<HourlyAdapters.viewHolder>() {

    var items: ArrayList<Hourly> = ArrayList()
    var context : Context? = null




    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hourText: TextView? = null
        var tempText: TextView? = null
        var pic : ImageView? = null
        constructor(itemView: View, hourText: TextView?, tempText: TextView?, pic: ImageView?) : this(itemView) {
            super.itemView
            this.hourText = itemView.findViewById(R.id.hourlyText)
            this.tempText = itemView.findViewById(R.id.tempText)
            this.pic = itemView.findViewById(R.id.pic)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder
    {
        var inflate : View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_hourly, parent, false)
        var context : Context = parent.context
        return viewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.hourText?.text = items[position].getHour()
        holder.tempText?.text = items[position].getTemp().toString()+"°"

        var drawableResourceId: Int = holder.itemView.resources.getIdentifier(items[position].getPicPath(), "drawable", holder.itemView.context.packageName)

        Glide.with(holder.itemView.context).load(drawableResourceId).into(holder.itemView.findViewById(R.id.pic))
    }

}