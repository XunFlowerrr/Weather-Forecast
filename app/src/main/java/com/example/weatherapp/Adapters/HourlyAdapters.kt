package com.example.weatherapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R

class HourlyAdapters : RecyclerView.Adapter<HourlyAdapters.viewHolder>() {


    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hourText: TextView? = null
        var tempText: TextView? = null
        var pic : ImageView? = null
        var items : ArrayList<Hourly> = ArrayList()
        var context : Context? = null;
        constructor(itemView: View, hourText: TextView?, tempText: TextView?, pic: ImageView?) : this(itemView) {
            super.itemView
//            this.hourText = itemView.findViewById(R.id.)
            this.tempText = tempText
            this.pic = pic
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder
    {
        var inflat : View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_hourly, parent, false)
        context = parent.context
        return viewHolder(inflat)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}