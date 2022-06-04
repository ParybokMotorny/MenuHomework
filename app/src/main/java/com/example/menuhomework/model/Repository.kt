package com.example.menuhomework.model

import androidx.lifecycle.LiveData
import com.example.menuhomework.model.providers.DataProvider
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.model.database.WeatherSource
import com.example.menuhomework.model.database.dao.WeatherDao
import com.example.menuhomework.model.retrofit.RetrofitProvider

object Repository {

    private lateinit var dataProvider: DataProvider
    private val internetProvider: RetrofitProvider = RetrofitProvider()

    fun initProvider(dao: WeatherDao) {
        dataProvider = WeatherSource(dao)
    }

    fun getWeathers() = dataProvider.subscribeToAllWeathers()

    fun saveWeather(weather: Weather) = dataProvider.saveWeathers(weather)

    fun getWeatherById(id: Long) = dataProvider.getWeathersById(id)

    fun deleteWeatherById(id: Long) = dataProvider.deleteWeatherById(id)

    fun deleteAll() = dataProvider.deleteAll()

    fun getAllSortedByName(isAsc: Int) = dataProvider.getAllSortedByName(isAsc)

    fun getAllSortedByDate(isAsc: Int) = dataProvider.getAllSortedByDate(isAsc)

    fun request(city: String): LiveData<RequestResult> =
        internetProvider.request(city)

    fun request(latitude: Float, longtitude: Float): LiveData<RequestResult> =
        internetProvider.request(latitude, longtitude)
}