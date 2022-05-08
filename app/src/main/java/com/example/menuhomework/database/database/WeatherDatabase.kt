package com.example.menuhomework.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.menuhomework.database.dao.WeatherDao
import com.example.menuhomework.database.converters.DateConverter
import com.example.menuhomework.database.Request

@Database(
    entities = [Request::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val educationDao: WeatherDao
}