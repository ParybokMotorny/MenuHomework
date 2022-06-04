package com.example.menuhomework.viewmodels

import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.RequestResult
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.viewStates.CityViewState

class MapsViewModel(private val repository: Repository = Repository) :
    BaseViewModel<Weather?, CityViewState>() {

    private var pendingWeather: Weather? = null

    fun saveChanges(weather: Weather) {
        pendingWeather = weather
        repository.saveWeather(weather)
    }

    override fun onCleared() {
        pendingWeather?.let { repository.saveWeather(it) }

    }

    fun loadNote(latitude: Float, longtitude: Float) {
        repository.request(latitude, longtitude).observeForever { requestResult ->
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
