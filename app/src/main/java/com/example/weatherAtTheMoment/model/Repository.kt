package com.example.weatherAtTheMoment.model

import androidx.lifecycle.LiveData
import com.example.weatherAtTheMoment.model.database.DataProvider
import com.example.weatherAtTheMoment.model.entity.db.Weather
import com.example.weatherAtTheMoment.model.database.WeatherSource
import com.example.weatherAtTheMoment.model.database.dao.WeatherDao
import com.example.weatherAtTheMoment.model.api.RetrofitProvider
import com.example.weatherAtTheMoment.model.entity.response.ResponseEntity

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

    fun request(city: String): LiveData<ResponseEntity> =
        internetProvider.request(city)

    fun request(latitude: Float, longtitude: Float): LiveData<ResponseEntity> =
        internetProvider.request(latitude, longtitude)
}