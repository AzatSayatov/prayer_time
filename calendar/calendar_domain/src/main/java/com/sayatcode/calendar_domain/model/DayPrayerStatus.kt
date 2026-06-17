package com.sayatcode.calendar_domain.model

import java.time.LocalDate

enum class PrayerName {
    FAJR, DHUHR, ASR, MAGHRIB, ISHA
}

data class DayPrayerStatus(
    val date: LocalDate,
    val completed: Set<PrayerName> = emptySet()
) {
    val completedCount: Int get() = completed.size
    val progress: Float get() = completedCount / 5f
    val isFullyCompleted: Boolean get() = completedCount == 5
}
