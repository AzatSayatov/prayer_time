package com.sayatcode.prayertimes

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

/** Prayer times of one day. All times are local wall-clock times in [timeZone]. */
public data class PrayerTimes(
    val date: LocalDate,
    val fajr: LocalTime,
    val sunrise: LocalTime,
    val dhuhr: LocalTime,
    val asr: LocalTime,
    val maghrib: LocalTime,
    val isha: LocalTime,
    /** Which source produced these times. Show it to the user. */
    val source: PrayerSource,
    val timeZone: ZoneId
)
