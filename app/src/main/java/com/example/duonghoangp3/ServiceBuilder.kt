package com.example.duonghoangp3


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val gsonConverterFactory = GsonConverterFactory.create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/")
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return  retrofit.create(service)
    }
}
