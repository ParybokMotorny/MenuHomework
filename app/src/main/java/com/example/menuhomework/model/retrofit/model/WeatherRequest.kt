package com.example.menuhomework.model.retrofit.model

import java.io.Serializable

class WeatherRequest: Serializable {

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
}

