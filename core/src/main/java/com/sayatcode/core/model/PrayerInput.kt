package com.sayatcode.core.model


import java.time.LocalDate
import kotlin.time.ExperimentalTime

data class PrayerInput @OptIn(ExperimentalTime::class) constructor(
    val latitude: Double,
    val longitude: Double,
    val date: LocalDate = LocalDate.now()
)
