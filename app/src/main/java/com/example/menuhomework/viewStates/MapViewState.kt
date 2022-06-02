package com.example.menuhomework.viewStates

import com.example.menuhomework.model.database.Request

class MapViewState(note: Request? = null, error: Throwable? = null) :
    BaseViewState<Request?>(note, error)