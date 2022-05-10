package com.example.duonghoangp3


data class WeatherResult(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
data class Main(
    val temp: Double,
)
data class WeatherList(
    val weather: List<WeatherResult>,
    val main: Main,
    val dt_txt: String
)
data class City(
    val name: String
)
data class Weather(
    val list: List<WeatherList>,
    val city: City
)

data class CurrentWeather(
    val weather: List<WeatherResult>,
    val name: String
)