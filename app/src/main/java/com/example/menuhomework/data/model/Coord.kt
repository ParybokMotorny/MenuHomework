package com.example.menuhomework.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Coord {
    @SerializedName("lon")
    @Expose
    var lon = 0f

    @SerializedName("lat")
    @Expose
    var lat = 0f
}
