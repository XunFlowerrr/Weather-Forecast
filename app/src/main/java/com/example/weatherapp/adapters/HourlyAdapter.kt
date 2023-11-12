package com.example.weatherapp.adapters

//import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.domains.Hourly

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    var items: ArrayList<Hourly> = ArrayList()
//    var context : Context? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hourText: TextView = itemView.findViewById(R.id.hourlyText)
        var tempText: TextView = itemView.findViewById(R.id.tempText)
//        var pic : ImageView = itemView.findViewById(R.id.pic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
//        var inflate : View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_hourly, parent, false)
        val inflate : View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_hourly, parent , false)
//        var context : Context = parent.context
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.hourText.text = items[position].hour
        holder.tempText.text = items[position].temp.toString() + "C"


//        var drawableResourceId: Int = holder.itemView.resources.getIdentifier(items[position].picPath, "drawable", holder.itemView.context.packageName)
        val drawableResourceId: Int = holder.itemView.resources.getIdentifier(items[position].picPath, "drawable", holder.itemView.context.packageName)

        Glide.with(holder.itemView.context).load(drawableResourceId).into(holder.itemView.findViewById(R.id.pic))
    }

}