package com.example.weatherAtTheMoment.model.entity.api

import com.example.weatherAtTheMoment.model.entity.db.Weather
import java.io.Serializable
import java.util.*

class WeatherRequest : Serializable {

    var coord = Coord()

    var weather: Array<Weather> = emptyArray()

    var base: String? = null

    var main = Main()

    var visibility = 0

    var wind = Wind()

    var clouds = Clouds()

    var dt: Long = 0

    var sys: Sys = Sys()

    var id: Long = 0

    var name: String? = null

    var cod = 0

    fun convertToRequest(): Weather {
        return Weather(
            date = Date(),
            city = name,
            humidity = main.humidity,
            pressure = main.pressure,
            temp = main.temp,
            icon = weather[0].icon
        )
    }
}

