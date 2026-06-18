package com.sayatcode.calendar_presentaion.model


import com.sayatcode.calendar_domain.model.DayPrayerStatus
import java.time.LocalDate
import java.time.YearMonth

data class CalendarUiState(
    val yearMonth: YearMonth = YearMonth.now(),
    val days: List<CalendarDay> = emptyList(),
    val selectedDate: LocalDate? = LocalDate.now(),
    val isLoading: Boolean = false
)

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
    val status: DayPrayerStatus
)
val CalendarUiState.selectedDayStatus: DayPrayerStatus?
    get() = days.firstOrNull { it.date == selectedDate }?.status