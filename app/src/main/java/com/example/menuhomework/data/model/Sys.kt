package com.example.menuhomework.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Sys {
    @SerializedName("type")
    @Expose
    var type = 0

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("message")
    @Expose
    var message = 0f

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("sunrise")
    @Expose
    var sunrise: Long = 0

    @SerializedName("sunset")
    @Expose
    var sunset: Long = 0
}
