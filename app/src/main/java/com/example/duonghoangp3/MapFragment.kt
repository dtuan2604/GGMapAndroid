package com.example.duonghoangp3

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapFragment: Fragment(), OnMapReadyCallback {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    private lateinit var mMap: GoogleMap

    private lateinit var locationManager: LocationManager
    private var currentLocation: Location?= null
    private var locationByNetwork: Location?= null
    private var locationByGps: Location?= null

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private val gpsLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByGps= location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
    //------------------------------------------------------//
    private val networkLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByNetwork= location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private lateinit var locationModel: LocationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationModel = activity?.run { ViewModelProvider(this).get(LocationViewModel::class.java) } ?: throw Exception("Invalid Activity")

        if(checkPermission()){
            getLocation()
        }
        else{
            requestPermission()
        }

    }

    @SuppressLint("MissingPermission")
    fun getLocation(){
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }

        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkLocationListener
            )
        }


        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }

        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            locationByNetwork = lastKnownLocationByNetwork
        }

        if (locationByGps != null && locationByNetwork != null) {
            if (locationByGps!!.accuracy > locationByNetwork!!.accuracy) {
                currentLocation = locationByGps
                latitude = currentLocation!!.latitude
                longitude = currentLocation!!.longitude
                // use latitude and longitude as per your need
            } else {
                currentLocation = locationByNetwork
                latitude = currentLocation!!.latitude
                longitude = currentLocation!!.longitude
                // use latitude and longitude as per your need
            }
        }else if(locationByGps == null && locationByNetwork != null){
            currentLocation = locationByNetwork
            latitude = currentLocation!!.latitude
            longitude = currentLocation!!.longitude
        }else if (locationByNetwork == null && locationByGps!= null){
            currentLocation = locationByGps
            latitude = currentLocation!!.latitude
            longitude = currentLocation!!.longitude
        }
        else{
            Toast.makeText(context,"You need to enable location first!", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }

        if(locationByGps != null || locationByNetwork != null){
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//            Toast.makeText(context,"Latitude is $latitude",Toast.LENGTH_LONG).show()
//            Toast.makeText(context,"Longitude is $longitude",Toast.LENGTH_LONG).show()

            locationModel.setLocation(latitude,longitude)

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }else{
            Toast.makeText(context,"Cannot generate the location!", Toast.LENGTH_LONG).show()
        }
    }
    companion object {
        const val PERMISSION_CODE = 1000
    }

    private fun checkPermission(): Boolean{
        if(context?.let { ActivityCompat.checkSelfPermission(it,android.Manifest.permission.ACCESS_COARSE_LOCATION) } != PackageManager.PERMISSION_GRANTED
            || context?.let { ActivityCompat.checkSelfPermission(it,android.Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED)
        {
            return false
        }
        return true
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            context as Activity,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
            , PERMISSION_CODE)
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}

