package com.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weather.models.ForecastItem

class ForecastAdapter(private val forecasts: List<ForecastItem>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecasts[position]
        holder.textView1.text = "${forecast.temp}°C"
        holder.textView3.text = forecast.day
        holder.textViewUVIndex.text = "UV Levels: ${forecast.uv}"
        holder.textViewChanceOfRain.text = "Chances of rain: ${forecast.chancesRain}%"

        val imageResourceId = TemperatureBand.getImageResourceId(forecast.getTemperatureBand())
        holder.imageView.setImageResource(imageResourceId)
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.textView1)
        val imageView: ImageView = itemView.findViewById(R.id.textView2)
        val textView3: TextView = itemView.findViewById(R.id.textView3)
        val textViewChanceOfRain: TextView = itemView.findViewById(R.id.textViewChanceOfRain)
        val textViewUVIndex: TextView = itemView.findViewById(R.id.textViewUVIndex)
    }
}
