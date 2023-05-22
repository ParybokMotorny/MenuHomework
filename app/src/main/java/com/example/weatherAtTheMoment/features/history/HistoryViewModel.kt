package com.example.weatherAtTheMoment.features.history

import androidx.lifecycle.Observer
import com.example.weatherAtTheMoment.base.BaseViewModel
import com.example.weatherAtTheMoment.model.Repository
import com.example.weatherAtTheMoment.model.database.Weather
import com.example.weatherAtTheMoment.model.Search.Sortings

class HistoryViewModel(
    private val repository: Repository = Repository
) : BaseViewModel<List<Weather>?, HistoryViewState>() {

    private val notesObserver = Observer<List<Weather>> {
        if (it == null) return@Observer

        viewStateLiveData.value = HistoryViewState(weather = it)
    }

    private val repositoryNotes = repository.getWeathers()

    init {
        viewStateLiveData.value = HistoryViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }

    fun deleteForId(id: Long){
        repository.deleteWeatherById(id)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

    fun sort(type : Int) {
        viewStateLiveData.value = HistoryViewState(weather = when(type){
            Sortings.DATE -> repository.getAllSortedByDate(1).value
            Sortings.DATEDESC -> repository.getAllSortedByDate(2).value
            Sortings.NAME -> repository.getAllSortedByName(1).value
            Sortings.NAMEDESC -> repository.getAllSortedByName(2).value
            else -> null
        })
    }
}