package com.sayatcode.core.model

import kotlinx.datetime.LocalTime

data class PrayerInfo(
    val currentPrayerName: String,
    val currentPrayerTimeLeft: String,
    val nextPrayerName: String,
    val nextPrayerTime: LocalTime
)
