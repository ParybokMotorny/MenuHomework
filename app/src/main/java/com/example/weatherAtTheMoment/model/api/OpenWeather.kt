package com.example.weatherAtTheMoment.model.api

import com.example.weatherAtTheMoment.model.entity.api.WeatherRequest
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


interface OpenWeather {
    @GET("data/2.5/weather")
    fun loadWeather(
        @Query("q") cityCountry: String?,
        @Query("appid") keyApi: String?
    ): Call<WeatherRequest>?

    @GET("data/2.5/weather")
    fun loadWeather(
        @Query("lat") latitude: Float?,
        @Query("lon") longtitude: Float?,
        @Query("appid") keyApi: String?
    ): Call<WeatherRequest>?
}
