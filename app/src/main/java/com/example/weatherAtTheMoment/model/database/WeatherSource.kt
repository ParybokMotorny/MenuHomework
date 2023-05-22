package com.example.weatherAtTheMoment.model.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherAtTheMoment.model.providers.DataProvider
import com.example.weatherAtTheMoment.model.database.dao.WeatherDao

class WeatherSource(
    private var dao: WeatherDao
) : DataProvider {

    var weathers: List<Weather> = dao.getAllWeathers()

    override fun subscribeToAllWeathers(): LiveData<List<Weather>>
        = MutableLiveData(dao.getAllWeathers())


    override fun getWeathersById(id: Long): LiveData<Weather>
        = MutableLiveData(dao.getWeatherById(id))


    override fun saveWeathers(weather: Weather): LiveData<Long>
        = MutableLiveData(dao.insertWeather(weather))


    override fun deleteWeatherById(id: Long) {
        dao.deleteWeatherById(id)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }

    override fun getAllSortedByName(isAsc: Int): LiveData<List<Weather>> =
        MutableLiveData(dao.getAllSortedByName(isAsc))


    override fun getAllSortedByDate(isAsc: Int): LiveData<List<Weather>> =
        MutableLiveData(dao.getAllSortedByDate(isAsc))


}
