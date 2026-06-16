package com.sayatcode.perday_presentation.domain

import com.sayatcode.core.use_case.toHijri
import com.sayatcode.core.use_case.toHijriString
import java.time.LocalDate

data class PrayerInputState(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val date : LocalDate = LocalDate.now(),
    val hijriDate: Triple<Int, Int, Int> = date.toHijri(),
    val fajr: String = "",
    val sunrice: String = "",
    val dhuhr: String = "",
    val asr: String = "",
    val magrib: String = "",
    val isha: String = "",
    val sunProgress: Float = 0f,
    val sunriseMillis: Long = 0L,
    val sunsetMillis: Long = 0L,
    val prayerAngles: List<Float> = emptyList()
)