package com.sayatcode.calendar_data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayatcode.calendar_data.local.dao.PrayerCompletionDao
import com.sayatcode.calendar_data.mapper.toDomain
import com.sayatcode.calendar_data.mapper.toEntity
import com.sayatcode.calendar_domain.model.DayPrayerStatus
import com.sayatcode.calendar_domain.model.PrayerName
import com.sayatcode.calendar_domain.repository.PrayerCompletionRepository
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)
class PrayerCompletionRepositoryImpl @Inject constructor(
    private val dao: PrayerCompletionDao
) : PrayerCompletionRepository {

    override fun observeMonth(yearMonth: YearMonth) =
        dao.observeRange(
            startDate = yearMonth.atDay(1).toString(),
            endDate = yearMonth.atEndOfMonth().toString()
        ).map { entities -> entities.map { it.toDomain() } }

    override suspend fun togglePrayer(date: LocalDate, prayer: PrayerName) {
        val current = dao.getByDate(date.toString())?.toDomain()
            ?: DayPrayerStatus(date = date)
        val isCurrentlyCompleted = prayer in current.completed
        setPrayerCompleted(date, prayer, completed = !isCurrentlyCompleted)
    }


    override suspend fun setPrayerCompleted(date: LocalDate, prayer: PrayerName, completed: Boolean) {
        val current = dao.getByDate(date.toString())?.toDomain()
            ?: DayPrayerStatus(date = date)
        val updated = if (completed) {
            current.copy(completed = current.completed + prayer)
        } else {
            current.copy(completed = current.completed - prayer)
        }
        dao.upsert(updated.toEntity())
    }
}