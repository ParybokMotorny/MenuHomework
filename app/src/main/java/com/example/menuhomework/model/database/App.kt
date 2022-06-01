package com.example.menuhomework.model.database

import android.app.Application
import androidx.room.Room
import com.example.menuhomework.model.database.database.WeatherDatabase

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        // Сохраняем объект приложения (для Singleton’а)
        instance = this

        // Строим базу
        db = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "education_database"
        )
            .allowMainThreadQueries()
            .build()
    }


    lateinit var db: WeatherDatabase
}