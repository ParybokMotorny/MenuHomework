package com.example.weatherNow.features.search

import com.example.weatherNow.model.entity.db.WeatherEntity
import com.example.weatherNow.base.BaseViewState

class SearchViewState(weather: WeatherEntity? = null, error: String? = null) :
    BaseViewState<WeatherEntity?>(weather, error)