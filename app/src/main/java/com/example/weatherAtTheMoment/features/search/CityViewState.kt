package com.example.weatherAtTheMoment.features.search

import com.example.weatherAtTheMoment.model.database.Weather
import com.example.weatherAtTheMoment.base.BaseViewState

class CityViewState(weather: Weather? = null, error: String? = null) :
    BaseViewState<Weather?>(weather, error)