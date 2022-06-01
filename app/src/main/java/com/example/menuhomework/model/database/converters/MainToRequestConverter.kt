package com.example.menuhomework.model.database.converters

import com.example.menuhomework.model.retrofit.model.WeatherRequest
import com.example.menuhomework.model.database.Request
import java.util.*

object MainToRequestConverter {
    fun convert(element: WeatherRequest): Request {
        val request = Request()
        request.date = Date()
        request.city = element.name
        request.humidity = element.main.humidity
        request.pressure = element.main.pressure
        request.temp = element.main.temp
        request.icon = element.weather[0].icon

        return request
    }
}