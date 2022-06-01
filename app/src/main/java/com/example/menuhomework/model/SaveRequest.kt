package com.example.menuhomework.model

import com.example.menuhomework.model.database.Request
import com.example.menuhomework.viewmodels.CityViewModel

object SaveRequest {
    fun saveRequest(request: Request, viewModel: CityViewModel){

        val newRequest = request.copy(
            id = request.id,
            city = request.city,
            temp = request.temp,
            pressure = request.pressure,
            humidity = request.humidity,
            date = request.date
        )

        viewModel.saveChanges(newRequest)
    }
}