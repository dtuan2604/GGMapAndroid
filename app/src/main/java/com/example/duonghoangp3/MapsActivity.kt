package com.example.duonghoangp3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.example.duonghoangp3.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class MapsActivity : AppCompatActivity() {
    private val mapFr = MapFragment()
    private val weatherFr = WeatherFragment()
    private lateinit var locationModel: LocationViewModel

    private val channelID = "CHANNEL 1"
    private val channelName = "Raining Alarm"
    private val notificationID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val mainViewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainViewBinding.root)

        createNotificationChannel()
        val notification = NotificationCompat.Builder(this,channelID)
            .setContentTitle("Weather Activity")
            .setContentText("It is about to rain. Don't forget your umbrella!")
            .setSmallIcon(R.drawable.warning_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        locationModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        locationModel.isRaining.observe(this) {
            if (it in 500..599 || it in 200..399) {
                notificationManager.notify(notificationID, notification)
            }
        }

        setFragment(mapFr)
        mainViewBinding.bottomNavView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.miMap -> setFragment(mapFr)
                R.id.miWeather -> setFragment(weatherFr)
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == MapFragment.PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted!", Toast.LENGTH_LONG).show()
                mapFr.getLocation()
            }else{
                Toast.makeText(this,"Permission Denied!", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
                lightColor = Color.RED
                enableLights(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}