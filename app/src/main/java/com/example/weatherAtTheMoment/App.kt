package com.example.weatherAtTheMoment

import android.app.Application
import androidx.room.Room
import com.example.weatherAtTheMoment.model.database.database.WeatherDatabase

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        db = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "education_database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    lateinit var db: WeatherDatabase
}