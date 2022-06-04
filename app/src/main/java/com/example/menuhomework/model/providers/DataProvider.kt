package com.example.menuhomework.model.providers

import androidx.lifecycle.LiveData
import com.example.menuhomework.model.database.Weather

interface DataProvider {

    fun subscribeToAllWeathers(): LiveData<List<Weather>>

    fun getWeathersById(id: Long): LiveData<Weather>

    fun saveWeathers(weather: Weather): LiveData<Long>

    fun deleteWeatherById(id: Long)

    fun deleteAll()

    fun getAllSortedByName(isAsc: Int): LiveData<List<Weather>>

    fun getAllSortedByDate(isAsc: Int): LiveData<List<Weather>>
}