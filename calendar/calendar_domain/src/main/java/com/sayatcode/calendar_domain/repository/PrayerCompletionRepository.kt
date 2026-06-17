package com.sayatcode.calendar_domain.repository

import com.sayatcode.calendar_domain.model.DayPrayerStatus
import com.sayatcode.calendar_domain.model.PrayerName
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface PrayerCompletionRepository {

    fun observeMonth(yearMonth: YearMonth): Flow<List<DayPrayerStatus>>

    suspend fun togglePrayer(date: LocalDate, prayer: PrayerName)

    suspend fun setPrayerCompleted(date: LocalDate, prayer: PrayerName, completed: Boolean)
}