package com.example.weatherNow.features.map

import com.example.weatherNow.base.BaseViewState
import com.example.weatherNow.model.entity.db.WeatherEntity

class MapsViewState(note: WeatherEntity? = null, error: String? = null) :
    BaseViewState<WeatherEntity?>(note, error)