package com.example.weatherAtTheMoment.features.map

import com.example.weatherAtTheMoment.base.BaseViewState
import com.example.weatherAtTheMoment.model.database.Weather

class MapsViewState(note: Weather? = null, error: String? = null) :
    BaseViewState<Weather?>(note, error)