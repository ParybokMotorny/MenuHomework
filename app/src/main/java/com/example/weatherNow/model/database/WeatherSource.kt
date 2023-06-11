package com.example.weatherNow.model.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherNow.model.database.dao.WeatherDao
import com.example.weatherNow.model.entity.db.WeatherEntity

class WeatherSource(
    private var dao: WeatherDao
) : DataProvider {

    override fun subscribeToAllWeathers(): LiveData<List<WeatherEntity>>
        = MutableLiveData(dao.getAllWeathers())


    override fun getWeathersById(id: Long): LiveData<WeatherEntity>
        = MutableLiveData(dao.getWeatherById(id))


    override fun saveWeathers(weather: WeatherEntity): LiveData<Long>
        = MutableLiveData(dao.insertWeather(weather))


    override fun deleteWeatherById(id: Long) {
        dao.deleteWeatherById(id)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }

    override fun getAllSortedByName(isAsc: Int): LiveData<List<WeatherEntity>> =
        MutableLiveData(dao.getAllSortedByName(isAsc))


    override fun getAllSortedByDate(isAsc: Int): LiveData<List<WeatherEntity>> =
        MutableLiveData(dao.getAllSortedByDate(isAsc))


}
