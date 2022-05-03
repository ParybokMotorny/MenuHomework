package com.example.menuhomework.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeatherRequest: Serializable {
    @SerializedName("coord")
    @Expose
    var coord = Coord()

    @SerializedName("weather")
    @Expose
    var weather: Array<Weather> = emptyArray()

    @SerializedName("base")
    @Expose
    var base: String? = null

    @SerializedName("main")
    @Expose
    var main = Main()

    @SerializedName("visibility")
    @Expose
    var visibility = 0

    @SerializedName("wind")
    @Expose
    var wind = Wind()

    @SerializedName("clouds")
    @Expose
    var clouds = Clouds()

    @SerializedName("dt")
    @Expose
    var dt: Long = 0

    @SerializedName("sys")
    @Expose
    var sys: Sys = Sys()

    @SerializedName("id")
    @Expose
    var id: Long = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("cod")
    @Expose
    var cod = 0
}

