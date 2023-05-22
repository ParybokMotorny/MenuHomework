package com.example.weatherAtTheMoment.model.database

import androidx.lifecycle.LiveData
import com.example.weatherAtTheMoment.model.entity.db.Weather

interface DataProvider {

    fun subscribeToAllWeathers(): LiveData<List<Weather>>

    fun getWeathersById(id: Long): LiveData<Weather>

    fun saveWeathers(weather: Weather): LiveData<Long>

    fun deleteWeatherById(id: Long)

    fun deleteAll()

    fun getAllSortedByName(isAsc: Int): LiveData<List<Weather>>

    fun getAllSortedByDate(isAsc: Int): LiveData<List<Weather>>
}