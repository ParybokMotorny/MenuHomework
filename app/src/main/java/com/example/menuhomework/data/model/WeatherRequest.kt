package com.example.menuhomework.data.model

class WeatherRequest(var name: String) {
    var coord = Coord()
    var weather = Weather(name)
    var main: Main = Main()
    var wind: Wind = Wind()
    var clouds: Clouds = Clouds()
}