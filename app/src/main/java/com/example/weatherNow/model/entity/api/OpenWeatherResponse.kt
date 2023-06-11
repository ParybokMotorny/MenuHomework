package com.example.weatherNow.model.entity.api

import com.example.weatherNow.model.entity.db.WeatherEntity
import java.io.Serializable
import java.util.*

class OpenWeatherResponse : Serializable {

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

    fun convertToRequest(): WeatherEntity {
        return WeatherEntity(
            date = Date(),
            city = name,
            humidity = main.humidity,
            pressure = main.pressure,
            temp = main.temp,
            icon = weather[0].icon,
            description = weather[0].description,
            feelsLike = main.feels_like,
            windSpeed = wind.speed
        )
    }
}

