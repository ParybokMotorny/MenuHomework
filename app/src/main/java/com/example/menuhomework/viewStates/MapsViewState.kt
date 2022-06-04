package com.example.menuhomework.viewStates

import com.example.menuhomework.model.database.Weather

class MapsViewState(note: Weather? = null, error: String? = null) :
    BaseViewState<Weather?>(note, error)