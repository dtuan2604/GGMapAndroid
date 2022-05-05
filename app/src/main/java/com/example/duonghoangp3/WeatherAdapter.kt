package com.example.duonghoangp3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WeatherAdapter(private val weathers: List<WeatherList>): RecyclerView.Adapter<WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item,parent,false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        return holder.bind(weathers[position])
    }

    override fun getItemCount(): Int {
        return weathers.size
    }
}

class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val icon: ImageView = itemView.findViewById(R.id.ivWeatherIcon)
    private val timer: TextView = itemView.findViewById(R.id.tvWeatherTimer)
    private val temp: TextView = itemView.findViewById(R.id.tvWeatherTemp)
    private val description: TextView = itemView.findViewById(R.id.tvWeatherDesc)

    @SuppressLint("SetTextI18n")
    fun bind(weather: WeatherList){
        Glide.with(itemView.context).load("http://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png").into(icon)
        timer.text = weather.dt_txt
        temp.text = weather.main.temp + " K"
        description.text = weather.weather[0].description
    }
}