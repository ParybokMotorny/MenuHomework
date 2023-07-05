package com.example.weatherNow.model.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherNow.BuildConfig
import com.example.weatherNow.model.entity.response.ResponseEntity

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
//                BuildConfig.OPEN_WEATHER_API_TOKEN
                "6b0423304b20ad534ccceecc6d3c729a"
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
                "6b0423304b20ad534ccceecc6d3c729a"
//                BuildConfig.OPEN_WEATHER_API_TOKEN
            )

        return result
    }
}