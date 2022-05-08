package com.example.menuhomework.database.converters

import com.example.menuhomework.retrofit.model.WeatherRequest
import com.example.menuhomework.database.Request
import java.util.*

object MainToRequestConverter {
    fun convert(element: WeatherRequest): Request {
        val request = Request()
        request.date = Date()
        request.city = element.name
        request.humidity = element.main.humidity
        request.pressure = element.main.pressure
        request.temp = element.main.temp

        return request
    }
}