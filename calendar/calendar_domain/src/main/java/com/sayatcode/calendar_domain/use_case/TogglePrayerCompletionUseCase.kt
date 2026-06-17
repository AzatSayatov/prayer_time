package com.sayatcode.calendar_domain.use_case

import com.sayatcode.calendar_domain.model.PrayerName
import com.sayatcode.calendar_domain.repository.PrayerCompletionRepository
import java.time.LocalDate
import javax.inject.Inject

class TogglePrayerCompletionUseCase @Inject constructor(
    private val repository: PrayerCompletionRepository
) {
    suspend operator fun invoke(date: LocalDate, prayer: PrayerName) {
        repository.togglePrayer(date, prayer)
    }
}