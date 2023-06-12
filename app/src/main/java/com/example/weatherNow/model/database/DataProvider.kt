package com.example.weatherNow.model.database

import androidx.lifecycle.LiveData
import com.example.weatherNow.model.entity.db.WeatherEntity

interface DataProvider {

    fun subscribeToAllWeathers(): LiveData<List<WeatherEntity>>

    fun getWeathersById(id: Long): LiveData<WeatherEntity>

    fun saveWeathers(weather: WeatherEntity): LiveData<Long>

    fun deleteWeatherById(id: Long)

    fun deleteAll()

    fun getAllSortedByName(isAsc: Int): LiveData<List<WeatherEntity>>

    fun getAllSortedByDate(isAsc: Int): LiveData<List<WeatherEntity>>

    fun search(str: String): LiveData<List<WeatherEntity>>
}