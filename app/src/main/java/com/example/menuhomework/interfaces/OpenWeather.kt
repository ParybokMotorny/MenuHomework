package com.example.menuhomework.interfaces

import com.example.menuhomework.data.model.WeatherRequest
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


interface OpenWeather {
    @GET("data/2.5/weather")
    fun loadWeather(
        @Query("q") cityCountry: String?,
        @Query("appid") keyApi: String?
    ): Call<WeatherRequest>?
}
