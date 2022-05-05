package com.example.duonghoangp3

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.example.duonghoangp3.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment

class MapsActivity : AppCompatActivity() {
    private val mapFr = MapFragment()
    private val weatherFr = WeatherFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val mainViewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainViewBinding.root)



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

}