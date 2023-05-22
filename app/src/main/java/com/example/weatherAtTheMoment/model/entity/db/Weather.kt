package com.example.weatherAtTheMoment.model.entity.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class Weather(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    var city: String? = null,

    var temp: Float = 0f,

    var pressure: Int = 0,

    var humidity: Int = 0,

    var date: Date? = null,

    var icon: String? = null,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Weather

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun copyWeather() : Weather {
        return copy(
            id = id,
            city = city,
            temp = temp,
            pressure = pressure,
            humidity = humidity,
            date = date
        )
    }
}