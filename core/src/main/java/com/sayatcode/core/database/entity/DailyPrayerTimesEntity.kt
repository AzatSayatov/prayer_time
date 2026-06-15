package com.sayatcode.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sayatcode.core.model.DailyPrayerTimes
import com.sayatcode.core.model.toDisplayString
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "daily_prayer_times")
data class DailyPrayerTimesEntity(
    @PrimaryKey val date: String, // "1-1"
    val fajr: String,
    val asr: String
)



fun DailyPrayerTimesEntity.toDomain(): DailyPrayerTimes {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val fajrTime = LocalTime.parse(fajr, formatter)
    val asrTime = LocalTime.parse(asr, formatter)

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