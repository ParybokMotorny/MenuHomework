package com.example.weatherNow.features.map

import com.example.weatherNow.base.BaseViewModel
import com.example.weatherNow.model.Repository
import com.example.weatherNow.model.entity.response.ResponseEntity
import com.example.weatherNow.model.entity.db.WeatherEntity
import com.example.weatherNow.features.search.SearchViewState

class MapsViewModel(private val repository: Repository = Repository) :
    BaseViewModel<WeatherEntity?, SearchViewState>() {

    fun saveChanges(weather: WeatherEntity) {
        repository.saveWeather(weather)
    }


    fun loadWeather(latitude: Float, longitude: Float) {
        repository.request(latitude, longitude).observeForever { requestResult ->
            if (requestResult == null) return@observeForever

            when (requestResult) {
                is ResponseEntity.Success<*> -> {
                    viewStateLiveData.value =
                        SearchViewState(weather = requestResult.data as? WeatherEntity)
                    repository.saveWeather(requestResult.data as WeatherEntity)
                }
                else -> viewStateLiveData.value =
                    SearchViewState(error = (requestResult as ResponseEntity.Error).error)
            }
        }
    }
}
