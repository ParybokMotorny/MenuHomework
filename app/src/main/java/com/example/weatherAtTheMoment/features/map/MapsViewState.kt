package com.example.weatherAtTheMoment.features.map

import com.example.weatherAtTheMoment.base.BaseViewState
import com.example.weatherAtTheMoment.model.entity.db.Weather

class MapsViewState(note: Weather? = null, error: String? = null) :
    BaseViewState<Weather?>(note, error)