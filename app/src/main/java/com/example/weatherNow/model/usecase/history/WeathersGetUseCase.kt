package com.example.weatherNow.model.usecase.history

import com.example.weatherNow.model.database.dao.WeatherDao
import com.example.weatherNow.model.entity.db.WeatherEntity
import javax.inject.Inject

class WeathersGetUseCase @Inject constructor(
    private val weatherDao: WeatherDao
) {
//    suspend fun perform(): Result<List<WeatherEntity>> {
//        val weathers = weatherDao.getAllWeathers()
//        return weathers
//    }
}