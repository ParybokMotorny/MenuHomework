package com.example.menuhomework.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Entity
class Request: Serializable {

    // @PrimaryKey - указывает на ключевую запись,
    // autoGenerate = true - автоматическая генерация ключа
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var city: String? = null

    var temp = 0f

    var pressure = 0

    var humidity = 0

    var date: Date? = null

    var icon: String? = null
}