package com.example.weatherNow

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.weatherNow.model.database.database.WeatherDatabase
import kotlin.properties.Delegates

class App : Application() {
    companion object {
        lateinit var instance: App
        @JvmStatic
        var context: Context by Delegates.notNull()
            private set
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
        context = applicationContext
    }


    lateinit var db: WeatherDatabase
}