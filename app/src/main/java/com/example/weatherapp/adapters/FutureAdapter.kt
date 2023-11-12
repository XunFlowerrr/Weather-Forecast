package com.example.weatherapp.adapters

//import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
//import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.domains.FutureDomain

class FutureAdapter : RecyclerView.Adapter<FutureAdapter.ViewHolder>() {

    var items: ArrayList<FutureDomain> = ArrayList()
//    var context : Context? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dayText: TextView = itemView.findViewById(R.id.dayText)
        var picPath : ImageView = itemView.findViewById(R.id.pic)
        var status : TextView = itemView.findViewById(R.id.statusText)
        var lowText : TextView = itemView.findViewById(R.id.lowText)
        var highText : TextView = itemView.findViewById(R.id.highText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
//        var inflate : View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_hourly, parent, false)
        val inflate : View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_future, parent , false)
//        var context : Context = parent.context
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.dayText.text = items[position].day
        holder.status.text = items[position].status
        holder.lowText.text = items[position].lowTemp.toString() + "C"
        holder.highText.text = items[position].highTemp.toString() + "C"


//        var drawableResourceId: Int = holder.itemView.resources.getIdentifier(items[position].picPath, "drawable", holder.itemView.context.packageName)
        val drawableResourceId: Int = holder.itemView.resources.getIdentifier(items[position].picPath, "drawable", holder.itemView.context.packageName)

        Glide.with(holder.itemView.context).load(drawableResourceId).into(holder.itemView.findViewById(R.id.pic))
    }

}