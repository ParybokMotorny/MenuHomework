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

}