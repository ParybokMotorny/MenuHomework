package com.example.weatherNow.model

import androidx.lifecycle.LiveData
import com.example.weatherNow.model.database.DataProvider
import com.example.weatherNow.model.entity.db.WeatherEntity
import com.example.weatherNow.model.database.WeatherSource
import com.example.weatherNow.model.database.dao.WeatherDao
import com.example.weatherNow.model.api.RetrofitProvider
import com.example.weatherNow.model.entity.response.ResponseEntity
import javax.inject.Inject

object Repository
{
    private lateinit var dataProvider: DataProvider
    private val internetProvider: RetrofitProvider = RetrofitProvider()

    fun initProvider(dao: WeatherDao) {
        dataProvider = WeatherSource(dao)
    }

    fun getWeathers() = dataProvider.subscribeToAllWeathers()

    fun saveWeather(weather: WeatherEntity) = dataProvider.saveWeathers(weather)

    fun getWeatherById(id: Long) = dataProvider.getWeathersById(id)

    fun deleteWeatherById(id: Long) = dataProvider.deleteWeatherById(id)

    fun deleteAll() = dataProvider.deleteAll()

    fun getAllSortedByName(isAsc: Int) = dataProvider.getAllSortedByName(isAsc)

    fun getAllSortedByDate(isAsc: Int) = dataProvider.getAllSortedByDate(isAsc)

    fun search(str: String) = dataProvider.search(str)

    fun request(city: String): LiveData<ResponseEntity> =
        internetProvider.request(city)

    fun request(latitude: Float, longitude: Float): LiveData<ResponseEntity> =
        internetProvider.request(latitude, longitude)
}