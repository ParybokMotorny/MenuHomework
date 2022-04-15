package com.example.menuhomework.data.model

import kotlin.random.Random

class Wind {
    var speed = Random.nextInt(0, 60)
    var deg = Random.nextInt(0, 60)
}