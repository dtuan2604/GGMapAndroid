package com.example.duonghoangp3

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = view.findViewById<ProgressBar>(R.id.pbProgressBar)
        val rvWeather = view.findViewById<RecyclerView>(R.id.rvWeather)

        val weatherAPIKey = "91dc5b7f3139e986de818ac9bad6a77f"

        val latitude = 0.0
        val longitude = 0.0

        val request = ServiceBuilder.buildService(Endpoint::class.java)
        val call = request.getWeather(latitude,longitude,weatherAPIKey)

        call.enqueue(object: retrofit2.Callback<Weather>{
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if(response.isSuccessful){
                    progressBar?.visibility = View.GONE
                    val test = response.body()!!.list
//                    Log.d(ContentValues.TAG, test.toString())
                    rvWeather?.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this.context)
                        adapter = WeatherAdapter(test)
                    }

                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                Log.d(ContentValues.TAG,"${t.message}")
            }
        })

    }
}