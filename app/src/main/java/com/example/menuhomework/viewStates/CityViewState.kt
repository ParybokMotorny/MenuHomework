package com.example.menuhomework.viewStates

import com.example.menuhomework.model.database.Weather

class CityViewState(weather: Weather? = null, error: String? = null) :
    BaseViewState<Weather?>(weather, error)