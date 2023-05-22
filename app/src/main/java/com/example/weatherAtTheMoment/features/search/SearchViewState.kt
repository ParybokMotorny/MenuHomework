package com.example.weatherAtTheMoment.features.search

import com.example.weatherAtTheMoment.model.entity.db.Weather
import com.example.weatherAtTheMoment.base.BaseViewState

class SearchViewState(weather: Weather? = null, error: String? = null) :
    BaseViewState<Weather?>(weather, error)