package com.example.weatherAtTheMoment.features.search

import com.example.weatherAtTheMoment.model.Repository
import com.example.weatherAtTheMoment.model.RequestResult
import com.example.weatherAtTheMoment.model.database.Weather
import com.example.weatherAtTheMoment.base.BaseViewModel

class CityViewModel(private val repository: Repository = Repository) :
    BaseViewModel<Weather?, CityViewState>() {

    fun saveChanges(weather: Weather) {
        repository.saveWeather(weather)
    }

    fun loadNote(city: String) {
        repository.request(city).observeForever { requestResult ->
            if (requestResult == null) return@observeForever

            when (requestResult) {
                is RequestResult.Success<*> -> {
                    viewStateLiveData.value =
                        CityViewState(weather = requestResult.data as? Weather)
                }
                else -> viewStateLiveData.value =
                    CityViewState(error = (requestResult as RequestResult.Error).error)
            }
        }
    }
}
