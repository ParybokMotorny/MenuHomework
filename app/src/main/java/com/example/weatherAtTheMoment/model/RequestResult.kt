package com.example.weatherAtTheMoment.model

sealed class RequestResult {
    data class Success<out T>(val data: T) : RequestResult()
    data class Error(val error: String) : RequestResult()
}