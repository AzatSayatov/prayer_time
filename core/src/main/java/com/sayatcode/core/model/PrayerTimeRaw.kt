package com.sayatcode.core.model

data class PrayerTimeRaw(
    val date: String,  // "1-1" → day-month
    val fajr: String,  // "07:12"
    val asr: String    // "16:30"
)
