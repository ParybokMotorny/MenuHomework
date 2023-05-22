package com.example.weatherAtTheMoment.features.search

import com.example.weatherAtTheMoment.model.Repository
import com.example.weatherAtTheMoment.model.entity.response.ResponseEntity
import com.example.weatherAtTheMoment.model.entity.db.Weather
import com.example.weatherAtTheMoment.base.BaseViewModel

class SearchViewModel(private val repository: Repository = Repository) :
    BaseViewModel<Weather?, SearchViewState>() {

    fun saveChanges(weather: Weather) {
        repository.saveWeather(weather)
    }

    fun loadNote(city: String) {
        repository.request(city).observeForever { requestResult ->
            if (requestResult == null) return@observeForever

            when (requestResult) {
                is ResponseEntity.Success<*> -> {
                    viewStateLiveData.value =
                        SearchViewState(weather = requestResult.data as? Weather)
                }
                else -> viewStateLiveData.value =
                    SearchViewState(error = (requestResult as ResponseEntity.Error).error)
            }
        }
    }
}
