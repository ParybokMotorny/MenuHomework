package com.example.weatherNow.model.database.dao

import androidx.room.*
import com.example.weatherNow.model.entity.db.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeather(weather: WeatherEntity) : Long

    @Update
    fun updateWeather(weather: WeatherEntity)

    @Delete
    fun deleteWeather(weather: WeatherEntity)

    @Query("DELETE FROM WeatherEntity WHERE id = :id")
    fun deleteWeatherById(id: Long)

    @Query("SELECT * FROM WeatherEntity")
    fun getAllWeathers(): List<WeatherEntity>

    @Query("SELECT * FROM WeatherEntity WHERE id = :id")
    fun getWeatherById(id: Long): WeatherEntity

    @Query("SELECT COUNT() FROM WeatherEntity")
    fun getCountWeathers(): Long

    @Query("DELETE FROM WeatherEntity")
    fun deleteAll()

    @Query("SELECT * FROM WeatherEntity ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN city END ASC, " +
            "CASE WHEN :isAsc = 2 THEN city END DESC ")
    fun getAllSortedByName(isAsc : Int): List<WeatherEntity>

    @Query("SELECT * FROM WeatherEntity ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN date END ASC, " +
            "CASE WHEN :isAsc = 2 THEN date END DESC ")
    fun getAllSortedByDate(isAsc : Int): List<WeatherEntity>
}