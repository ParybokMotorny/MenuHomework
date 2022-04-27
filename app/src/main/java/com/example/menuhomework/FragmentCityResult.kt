package com.example.menuhomework

import com.example.menuhomework.data.model.WeatherRequest

interface FragmentCityResult {
    fun onFragmentResult(item: WeatherRequest)
}