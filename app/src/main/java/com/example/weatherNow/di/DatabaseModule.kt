package com.example.weatherNow.di

import android.content.Context
import androidx.room.Room
import com.example.weatherNow.model.database.WeatherSource
import com.example.weatherNow.model.database.dao.WeatherDao
import com.example.weatherNow.model.database.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(applicationContext: Context): WeatherDatabase {
        return Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "education_database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(appDatabase: WeatherDatabase): WeatherDao {
        return appDatabase.weatherDao
    }

    @Singleton
    @Provides
    fun provideWeatherSource(weatherDao: WeatherDao): WeatherSource {
        return WeatherSource(weatherDao)
    }
}