package com.example.menuhomework.viewStates

import com.example.menuhomework.model.database.Weather

class SearchViewState(
    val weather: List<Weather>? = null,
    error: String? = null
) : BaseViewState<List<Weather>?>(weather, error)