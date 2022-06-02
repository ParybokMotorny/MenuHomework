package com.example.menuhomework.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.menuhomework.model.database.WeatherSource
import com.example.menuhomework.viewStates.SearchViewState

class SearchViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<SearchViewState> = MutableLiveData()

    init {
        WeatherSource.requests.observeForever { requests ->
            viewStateLiveData.value = viewStateLiveData.value?.copy(requests = requests)
                ?: SearchViewState(requests)
        }
    }

    fun viewState(): LiveData<SearchViewState> = viewStateLiveData
}