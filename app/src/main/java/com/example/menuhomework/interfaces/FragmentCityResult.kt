package com.example.menuhomework.interfaces

import com.example.menuhomework.data.model.WeatherRequest

interface FragmentCityResult {
    fun onFragmentResult(item: WeatherRequest)
}