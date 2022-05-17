package com.example.menuhomework.database.dao

import androidx.room.*
import com.example.menuhomework.database.Request

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRequest(request: Request) : Long

    @Update
    fun updateRequest(request: Request)

    @Delete
    fun deleteRequest(request: Request)

    @Query("DELETE FROM request WHERE id = :id")
    fun deleteRequestById(id: Long)

    @Query("SELECT * FROM request")
    fun getAllRequests(): List<Request>

    // Получаем данные одного студента по id
    @Query("SELECT * FROM request WHERE id = :id")
    fun getRequestById(id: Long): Request

    //Получаем количество записей в таблице
    @Query("SELECT COUNT() FROM request")
    fun getCountRequests(): Long

    // видаляємо усі елемети з бази
    @Query("DELETE FROM request")
    fun deleteAll()

    @Query("SELECT * FROM request ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN city END ASC, " +
            "CASE WHEN :isAsc = 2 THEN city END DESC ")
    fun getAllSortedByName(isAsc : Int): List<Request>

    @Query("SELECT * FROM request ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN date END ASC, " +
            "CASE WHEN :isAsc = 2 THEN date END DESC ")
    fun getAllSortedByDate(isAsc : Int): List<Request>
}