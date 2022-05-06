package com.example.duonghoangp3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class LocationViewModel :ViewModel(){

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    val currentLat: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }

    fun setLocation(lat: Double, long: Double){
        latitude = lat
        longitude = long

        currentLat.value = lat
    }
}