package com.example.weatherNow.model.api

import com.example.weatherNow.model.entity.db.WeatherEntity
import com.example.weatherNow.model.entity.api.OpenWeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.openweathermap.org/"

open class Retrofit(
    private val onCompleted: (WeatherEntity) -> Unit,
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

    fun run(latitude: Float, lontitude: Float, keyApi: String) {
        requestRetrofit(
            latitude,
            lontitude,
            keyApi
        )
    }

    private fun initRetrofit() {
        openWeather = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeather::class.java)
    }

    private fun requestRetrofit(cityName: String, keyApi: String) {
        openWeather?.loadWeather(cityName, keyApi)
            ?.enqueue(callback)
    }

    private fun requestRetrofit(latitude: Float, longitude: Float, keyApi: String) {
        openWeather?.loadWeather(latitude, longitude, keyApi)
            ?.enqueue(callback)
    }

    private var callback = object : Callback<OpenWeatherResponse?> {
        override fun onResponse(
            call: Call<OpenWeatherResponse?>,
            response: Response<OpenWeatherResponse?>
        ) {
            response.body()?.let {
                onCompleted(it.convertToRequest())
            } ?: run {
                onFail("This city does not exist")
            }
        }

        override fun onFailure(call: Call<OpenWeatherResponse?>, t: Throwable) {
            onFail("Can`t connect to server. Check your Internet connection")
        }
    }
}