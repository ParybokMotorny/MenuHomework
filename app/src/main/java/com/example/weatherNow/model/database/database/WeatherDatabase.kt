package com.example.weatherNow.model.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherNow.model.database.dao.WeatherDao
import com.example.weatherNow.model.database.converters.DateConverter
import com.example.weatherNow.model.entity.db.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 4
)
@TypeConverters(DateConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
}