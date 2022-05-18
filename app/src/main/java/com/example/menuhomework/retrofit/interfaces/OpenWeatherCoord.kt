package com.example.menuhomework.retrofit.interfaces

import com.example.menuhomework.retrofit.model.WeatherRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherCoord {
    @GET("data/2.5/weather")
    fun loadWeather(
        @Query("lat") latitude: Double?,
        @Query("lon") longtitude: Double?,
        @Query("appid") keyApi: String?
    ): Call<WeatherRequest>?
}