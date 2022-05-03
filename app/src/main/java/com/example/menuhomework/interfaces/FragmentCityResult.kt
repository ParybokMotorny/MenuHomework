package com.example.menuhomework.interfaces

import com.example.menuhomework.data.model.WeatherRequest
import java.io.Serializable

interface FragmentCityResult: Serializable {
    fun onFragmentResult(item: WeatherRequest)
}