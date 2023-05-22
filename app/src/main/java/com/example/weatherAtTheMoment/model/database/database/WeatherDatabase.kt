package com.example.weatherAtTheMoment.model.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherAtTheMoment.model.database.dao.WeatherDao
import com.example.weatherAtTheMoment.model.database.converters.DateConverter
import com.example.weatherAtTheMoment.model.entity.db.Weather

@Database(
    entities = [Weather::class],
    version = 2
)
@TypeConverters(DateConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
}