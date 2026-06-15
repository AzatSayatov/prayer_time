package com.sayatcode.core.model

import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class DailyPrayerTimes(
    val date: String,
    val fajr: String,
    val sunrise: String,   // fajr + 70 мин
    val dhuhr: String,     // всегда 13:30
    val asr: String,
    val maghrib: String,   // asr + 90 мин
    val isha: String       // asr + 180 мин
)

fun PrayerTimeRaw.toDomain(): DailyPrayerTimes {
    val fajrTime = LocalTime.parse(fajr, DateTimeFormatter.ofPattern("HH:mm"))
    val asrTime = LocalTime.parse(asr, DateTimeFormatter.ofPattern("HH:mm"))

    return DailyPrayerTimes(
        date = date,
        fajr = fajrTime.toDisplayString(),
        sunrise = fajrTime.plusMinutes(70).toDisplayString(),
        dhuhr = LocalTime.of(13, 30).toDisplayString(),
        asr = asrTime.toDisplayString(),
        maghrib = asrTime.plusMinutes(100).toDisplayString(),
        isha = asrTime.plusMinutes(180).toDisplayString()
    )
}
fun LocalTime.toDisplayString(): String {
    return DateTimeFormatter.ofPattern("HH:mm").format(this)
}
