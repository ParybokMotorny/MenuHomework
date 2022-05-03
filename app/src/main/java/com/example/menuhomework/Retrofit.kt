package com.example.menuhomework

import com.example.menuhomework.interfaces.OpenWeather
import com.example.menuhomework.data.model.WeatherRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IndexOutOfBoundsException

class Retrofit(private var listener: OnResponseCompleted) {

    private val AbsoluteZero = -273.15f
    private var openWeather: OpenWeather? = null

    init{
        initRetrofit()
    }

    fun run(city: String, keyApi: String){
        requestRetrofit(
            city,
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
            ?.enqueue(object : Callback<WeatherRequest?> {
                override fun onResponse(
                    call: Call<WeatherRequest?>,
                    response: Response<WeatherRequest?>
                ) {
                    response.body()?.let {
                        listener.onCompleted(it)
                    }?:run{
                        listener.onFail(IndexOutOfBoundsException())
                    }
                }

                override fun onFailure(call: Call<WeatherRequest?>, t: Throwable) {
                    listener.onFail(t)
                }
            })

    }

    // шнтерфейс для опрацювання результату роботи ретрофіту
    interface OnResponseCompleted {
        fun onCompleted(content: WeatherRequest)
        fun onFail(t: Throwable)
    }
}