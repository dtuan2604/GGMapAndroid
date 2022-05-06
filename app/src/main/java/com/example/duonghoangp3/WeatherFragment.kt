package com.example.duonghoangp3

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response

class WeatherFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    private lateinit var locationModel: LocationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationModel = activity?.run { ViewModelProvider(this).get(LocationViewModel::class.java) } ?: throw Exception("Invalid Activity")

        val progressBar = view.findViewById<ProgressBar>(R.id.pbProgressBar)
        val rvWeather = view.findViewById<RecyclerView>(R.id.rvWeather)
        val title = view.findViewById<TextView>(R.id.tvWeather)

        val weatherAPIKey = "91dc5b7f3139e986de818ac9bad6a77f"
        val latitude = locationModel.latitude
        val longitude = locationModel.longitude

        val request = ServiceBuilder.buildService(Endpoint::class.java)
        locationModel.currentLat.observe(viewLifecycleOwner,Observer {
            val call = request.getWeather(latitude,longitude,weatherAPIKey)

            call.enqueue(object: retrofit2.Callback<Weather>{
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    if(response.isSuccessful){
                        progressBar?.visibility = View.GONE
                        title.text = response.body()!!.city.name
                        val result = response.body()!!.list
//                    Log.d(ContentValues.TAG, test.toString())
                        rvWeather?.apply {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(this.context)
                            adapter = WeatherAdapter(result)
                        }

                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG,"${t.message}")
                }
            })
        })


    }
}