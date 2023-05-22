package com.example.weatherAtTheMoment.model.providers

import androidx.lifecycle.LiveData
import com.example.weatherAtTheMoment.model.RequestResult

interface InternetProvider {

    fun request(city: String): LiveData<RequestResult>

    fun request(latitude: Float, longtitude: Float): LiveData<RequestResult>
}