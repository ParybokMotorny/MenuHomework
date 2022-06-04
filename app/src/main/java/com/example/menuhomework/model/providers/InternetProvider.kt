package com.example.menuhomework.model.providers

import androidx.lifecycle.LiveData
import com.example.menuhomework.model.RequestResult

interface InternetProvider {

    fun request(city: String): LiveData<RequestResult>

    fun request(latitude: Float, longtitude: Float): LiveData<RequestResult>
}