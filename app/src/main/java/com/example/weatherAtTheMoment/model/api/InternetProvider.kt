package com.example.weatherAtTheMoment.model.api

import androidx.lifecycle.LiveData
import com.example.weatherAtTheMoment.model.entity.response.ResponseEntity

interface InternetProvider {

    fun request(city: String): LiveData<ResponseEntity>

    fun request(latitude: Float, longtitude: Float): LiveData<ResponseEntity>
}