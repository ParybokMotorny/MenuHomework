package com.example.weatherAtTheMoment.model.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherAtTheMoment.model.entity.response.ResponseEntity

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
                "6b0423304b20ad534ccceecc6d3c729a"
            )

        return result
    }

    override fun request(latitude: Float, longtitude: Float): LiveData<ResponseEntity> {
        val result = MutableLiveData<ResponseEntity>()

        Retrofit({ request ->
            result.value = ResponseEntity.Success(request)
        }, { exception ->
            result.value = ResponseEntity.Error(exception)
        })
            .run(
                latitude,
                longtitude,
                "6b0423304b20ad534ccceecc6d3c729a"
            )

        return result
    }
}