package com.sayatcode.calendar_data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayatcode.calendar_data.local.entity.PrayerCompletionEntity
import com.sayatcode.calendar_domain.model.DayPrayerStatus
import com.sayatcode.calendar_domain.model.PrayerName
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun PrayerCompletionEntity.toDomain(): DayPrayerStatus {
    val completed = buildSet {
        if (fajr) add(PrayerName.FAJR)
        if (dhuhr) add(PrayerName.DHUHR)
        if (asr) add(PrayerName.ASR)
        if (maghrib) add(PrayerName.MAGHRIB)
        if (isha) add(PrayerName.ISHA)
    }
    return DayPrayerStatus(date = LocalDate.parse(date), completed = completed)
}

fun DayPrayerStatus.toEntity(): PrayerCompletionEntity = PrayerCompletionEntity(
    date = date.toString(),
    fajr = PrayerName.FAJR in completed,
    dhuhr = PrayerName.DHUHR in completed,
    asr = PrayerName.ASR in completed,
    maghrib = PrayerName.MAGHRIB in completed,
    isha = PrayerName.ISHA in completed
)