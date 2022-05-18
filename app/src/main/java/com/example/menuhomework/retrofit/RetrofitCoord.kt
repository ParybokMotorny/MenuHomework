package com.example.menuhomework.retrofit

import com.example.menuhomework.retrofit.interfaces.OpenWeather
import com.example.menuhomework.retrofit.interfaces.OpenWeatherCoord
import com.example.menuhomework.retrofit.model.WeatherRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class RetrofitCoord(
    private var listener: OnResponseCompleted
) {

    private var openWeather: OpenWeatherCoord? = null

    init{
        initRetrofit()
    }

    fun run(latitude: Double, longtitude: Double, keyApi: String){
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
            .create(OpenWeatherCoord::class.java)
    }

    private fun requestRetrofit(latitude: Double, longtitude: Double, keyApi: String) {
        openWeather?.loadWeather(latitude, longtitude, keyApi)
            ?.enqueue(object : Callback<WeatherRequest?> {
                override fun onResponse(
                    call: Call<WeatherRequest?>,
                    response: Response<WeatherRequest?>
                ) {
                    response.body()?.let {
                        listener.onCompleted(it)
                    }?:run{
                        listener.onFail("This city does not exist")
                    }
                }

                override fun onFailure(call: Call<WeatherRequest?>, t: Throwable) {
                    listener.onFail("Can`t connect to server. Check your Internet connection")
                }
            })

    }

    // шнтерфейс для опрацювання результату роботи ретрофіту
    interface OnResponseCompleted {
        fun onCompleted(content: WeatherRequest)
        fun onFail(message: String)
    }
}