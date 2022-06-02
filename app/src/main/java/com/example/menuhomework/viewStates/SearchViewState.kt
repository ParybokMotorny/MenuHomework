package com.example.menuhomework.viewStates

import com.example.menuhomework.model.database.Request

class SearchViewState(
    val notes: List<Request>? = null,
    error: Throwable? = null
) : BaseViewState<List<Request>?>(notes, error)