package com.example.menuhomework.viewmodels

import androidx.lifecycle.Observer
import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.model.Search.Sortings
import com.example.menuhomework.viewStates.SearchViewState

class SearchViewModel(
    private val repository: Repository = Repository
) : BaseViewModel<List<Weather>?, SearchViewState>() {

    private val notesObserver = Observer<List<Weather>> {
        if (it == null) return@Observer

        viewStateLiveData.value = SearchViewState(weather = it)
    }

    private val repositoryNotes = repository.getWeathers()

    init {
        viewStateLiveData.value = SearchViewState()
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
        viewStateLiveData.value = SearchViewState(weather = when(type){
            Sortings.DATE -> repository.getAllSortedByDate(1).value
            Sortings.DATEDESC -> repository.getAllSortedByDate(2).value
            Sortings.NAME -> repository.getAllSortedByName(1).value
            Sortings.NAMEDESC -> repository.getAllSortedByName(2).value
            else -> null
        })
    }
}