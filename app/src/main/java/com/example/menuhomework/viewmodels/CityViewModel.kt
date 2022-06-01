package com.example.menuhomework.viewmodels

import androidx.lifecycle.ViewModel
import com.example.menuhomework.model.database.Request
import com.example.menuhomework.model.database.WeatherSource

class CityViewModel(private val weatherSource: WeatherSource = WeatherSource) : ViewModel() {

    private var pendingRequest: Request? = null

    fun saveChanges(request: Request) {
        pendingRequest = request
        weatherSource.addRequest(request)
    }
}