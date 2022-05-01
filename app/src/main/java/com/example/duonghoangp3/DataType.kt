package com.example.duonghoangp3


data class WeatherResult(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
data class Main(
    val temp: String,
    val feels_like: String
)
data class Weather(
    val weather: List<WeatherResult>,
    val main: Main
)