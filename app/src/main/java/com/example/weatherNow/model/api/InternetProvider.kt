package com.example.weatherNow.model.api

import androidx.lifecycle.LiveData
import com.example.weatherNow.model.entity.response.ResponseEntity

interface InternetProvider {
    fun request(city: String): LiveData<ResponseEntity>

    fun request(latitude: Float, longitude: Float): LiveData<ResponseEntity>
}