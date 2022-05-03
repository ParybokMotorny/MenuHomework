package com.example.menuhomework.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Main {
    @SerializedName("temp")
    @Expose
    var temp = 0f

    @SerializedName("pressure")
    @Expose
    var pressure = 0

    @SerializedName("humidity")
    @Expose
    var humidity = 0

    @SerializedName("temp_min")
    @Expose
    var temp_min = 0f

    @SerializedName("temp_max")
    @Expose
    var temp_max = 0f
}
