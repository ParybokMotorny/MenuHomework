package com.example.weatherAtTheMoment.model.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherAtTheMoment.model.providers.InternetProvider
import com.example.weatherAtTheMoment.model.RequestResult

class RetrofitProvider : InternetProvider {
    override fun request(city: String): LiveData<RequestResult> {
        val result = MutableLiveData<RequestResult>()

        Retrofit({ request ->
            result.value = RequestResult.Success(request)
        }, { exception ->
            result.value = RequestResult.Error(exception)
        })
            .run(
                city,
                "6b0423304b20ad534ccceecc6d3c729a"
            )

        return result
    }

    override fun request(latitude: Float, longtitude: Float): LiveData<RequestResult> {
        val result = MutableLiveData<RequestResult>()

        Retrofit({ request ->
            result.value = RequestResult.Success(request)
        }, { exception ->
            result.value = RequestResult.Error(exception)
        })
            .run(
                latitude,
                longtitude,
                "6b0423304b20ad534ccceecc6d3c729a"
            )

        return result
    }
}