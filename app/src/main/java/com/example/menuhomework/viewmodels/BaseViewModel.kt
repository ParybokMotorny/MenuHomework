package com.example.menuhomework.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.menuhomework.viewStates.BaseViewState

open class BaseViewModel<T, VS : BaseViewState<T>> : ViewModel() {
    open val viewStateLiveData = MutableLiveData<VS>()

    open fun getViewState(): LiveData<VS> = viewStateLiveData
}