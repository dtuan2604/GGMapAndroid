package com.example.duonghoangp3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LocationViewModel :ViewModel(){

    var latitude: Double = 0.0
    var longitude: Double = 0.0


    val isRaining: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun setLocation(lat: Double, long: Double){
        latitude = lat
        longitude = long
    }

    fun setRaining(weatherID: Int){
        isRaining.value = weatherID
    }
}