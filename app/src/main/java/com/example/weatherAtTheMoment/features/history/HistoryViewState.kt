package com.example.weatherAtTheMoment.features.history

import com.example.weatherAtTheMoment.base.BaseViewState
import com.example.weatherAtTheMoment.model.entity.db.Weather

class HistoryViewState(
    val weather: List<Weather>? = null,
    error: String? = null
) : BaseViewState<List<Weather>?>(weather, error)