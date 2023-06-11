package com.example.weatherNow.features.history

import com.example.weatherNow.base.BaseViewState
import com.example.weatherNow.model.entity.db.WeatherEntity

class HistoryViewState(
    val weather: List<WeatherEntity>? = null,
    error: String? = null
) : BaseViewState<List<WeatherEntity>?>(weather, error)