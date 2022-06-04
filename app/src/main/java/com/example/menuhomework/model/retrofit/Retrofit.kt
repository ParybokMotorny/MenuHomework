package com.example.menuhomework.model.retrofit

import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.model.retrofit.interfaces.OpenWeather
import com.example.menuhomework.model.retrofit.model.WeatherRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class Retrofit(
    private val onCompleted: (Weather) -> Unit,
    private val onFail: (String) -> Unit
) {

    private var openWeather: OpenWeather? = null

    init {
        initRetrofit()
    }

    fun run(city: String, keyApi: String) {
        requestRetrofit(
            city,
            keyApi
        )
    }

    fun run(latitude: Float, longtitude: Float, keyApi: String) {
        requestRetrofit(
            latitude,
            longtitude,
            keyApi
        )
    }

    private fun initRetrofit() {
        openWeather = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeather::class.java)
    }

    private fun requestRetrofit(city: String, keyApi: String) {
        openWeather?.loadWeather(city, keyApi)
            ?.enqueue(callback)
    }

    private fun requestRetrofit(latitude: Float, longtitude: Float, keyApi: String) {
        openWeather?.loadWeather(latitude, longtitude, keyApi)
            ?.enqueue(callback)
    }

    private var callback = object : Callback<WeatherRequest?> {
        override fun onResponse(
            call: Call<WeatherRequest?>,
            response: Response<WeatherRequest?>
        ) {
            response.body()?.let {
                onCompleted(it.convertToRequest())
            } ?: run {
                onFail("This city does not exist")
            }
        }

        override fun onFailure(call: Call<WeatherRequest?>, t: Throwable) {
            onFail("Can`t connect to server. Check your Internet connection")
        }
    }
}