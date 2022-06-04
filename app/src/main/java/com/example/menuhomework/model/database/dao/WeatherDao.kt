package com.example.menuhomework.model.database.dao

import androidx.room.*
import com.example.menuhomework.model.database.Weather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeather(weather: Weather) : Long

    @Update
    fun updateWeather(weather: Weather)

    @Delete
    fun deleteWeather(weather: Weather)

    @Query("DELETE FROM weather WHERE id = :id")
    fun deleteWeatherById(id: Long)

    @Query("SELECT * FROM weather")
    fun getAllWeathers(): List<Weather>

    // Получаем данные одного студента по id
    @Query("SELECT * FROM weather WHERE id = :id")
    fun getWeatherById(id: Long): Weather

    //Получаем количество записей в таблице
    @Query("SELECT COUNT() FROM weather")
    fun getCountWeathers(): Long

    // видаляємо усі елемети з бази
    @Query("DELETE FROM weather")
    fun deleteAll()

    @Query("SELECT * FROM weather ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN city END ASC, " +
            "CASE WHEN :isAsc = 2 THEN city END DESC ")
    fun getAllSortedByName(isAsc : Int): List<Weather>

    @Query("SELECT * FROM weather ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN date END ASC, " +
            "CASE WHEN :isAsc = 2 THEN date END DESC ")
    fun getAllSortedByDate(isAsc : Int): List<Weather>
}