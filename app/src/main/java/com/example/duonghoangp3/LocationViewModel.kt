package com.example.duonghoangp3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LocationViewModel :ViewModel(){

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    val currentLat: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }

    val isRaining: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun setLocation(lat: Double, long: Double){
        latitude = lat
        longitude = long

        currentLat.value = lat
    }

    fun setRaining(weatherID: Int){
        isRaining.value = weatherID
    }
}