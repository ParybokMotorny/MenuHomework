package com.example.weatherNow.model.api

import com.example.weatherNow.model.entity.api.OpenWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeather {
    @GET("data/2.5/weather")
    fun loadWeather(
        @Query("q") cityCountry: String?,
        @Query("appid") keyApi: String?
    ): Call<OpenWeatherResponse>?

    @GET("data/2.5/weather")
    fun loadWeather(
        @Query("lat") latitude: Float?,
        @Query("lon") longitude: Float?,
        @Query("appid") keyApi: String?
    ): Call<OpenWeatherResponse>?
}
