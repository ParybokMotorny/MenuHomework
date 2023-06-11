package com.example.weatherNow.model.api

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherNow.BuildConfig
import com.example.weatherNow.model.entity.response.ResponseEntity
import java.io.FileInputStream
import java.util.Properties

class RetrofitProvider : InternetProvider {

    override fun request(city: String): LiveData<ResponseEntity> {
        val result = MutableLiveData<ResponseEntity>()

        Retrofit({ request ->
            result.value = ResponseEntity.Success(request)
        }, { exception ->
            result.value = ResponseEntity.Error(exception)
        })
            .run(
                city,
                BuildConfig.OPEN_WEATHER_API_TOKEN
            )

        return result
    }

    override fun request(latitude: Float, longitude: Float): LiveData<ResponseEntity> {
        val result = MutableLiveData<ResponseEntity>()
        Retrofit({ request ->
            result.value = ResponseEntity.Success(request)
        }, { exception ->
            result.value = ResponseEntity.Error(exception)
        })
            .run(
                latitude,
                longitude,
                BuildConfig.OPEN_WEATHER_API_TOKEN
            )

        return result
    }
}