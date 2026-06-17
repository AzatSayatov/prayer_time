package com.sayatcode.calendar_domain.use_case

import com.sayatcode.calendar_domain.model.DayPrayerStatus
import com.sayatcode.calendar_domain.repository.PrayerCompletionRepository
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject

class ObserveMonthPrayerStatusUseCase @Inject constructor(
    private val repository: PrayerCompletionRepository
) {
    operator fun invoke(yearMonth: YearMonth): Flow<List<DayPrayerStatus>> =
        repository.observeMonth(yearMonth)
}