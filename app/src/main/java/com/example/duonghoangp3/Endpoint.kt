package com.example.duonghoangp3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {
    @GET("data/2.5/forecast")
    fun getWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("appid") key: String) : Call<Weather>

    @GET("data/2.5/weather")
    fun getCurrentWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("appid") key: String): Call<CurrentWeather>
}