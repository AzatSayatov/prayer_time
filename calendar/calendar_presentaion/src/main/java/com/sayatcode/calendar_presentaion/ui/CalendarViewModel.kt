package com.sayatcode.calendar_presentaion.ui


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayatcode.calendar_domain.model.DayPrayerStatus
import com.sayatcode.calendar_domain.model.PrayerName
import com.sayatcode.calendar_domain.use_case.ObserveMonthPrayerStatusUseCase
import com.sayatcode.calendar_domain.use_case.TogglePrayerCompletionUseCase
import com.sayatcode.calendar_presentaion.model.CalendarDay
import com.sayatcode.calendar_presentaion.model.CalendarUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val observeMonthPrayerStatus: ObserveMonthPrayerStatusUseCase,
    private val togglePrayerCompletion: TogglePrayerCompletionUseCase
) : ViewModel() {

    private val currentYearMonth = MutableStateFlow(YearMonth.now())
    private val selectedDate = MutableStateFlow<LocalDate?>(LocalDate.now())

    private val monthStatuses = currentYearMonth.flatMapLatest { yearMonth ->
        observeMonthPrayerStatus(yearMonth)
    }

    val uiState: StateFlow<CalendarUiState> = combineState()

    private fun combineState(): StateFlow<CalendarUiState> {
        val combined = kotlinx.coroutines.flow.combine(
            currentYearMonth,
            monthStatuses,
            selectedDate
        ) { yearMonth, statuses, selected ->
            CalendarUiState(
                yearMonth = yearMonth,
                days = buildCalendarDays(yearMonth, statuses),
                selectedDate = selected,
                isLoading = false
            )
        }
        return combined.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CalendarUiState(isLoading = true)
        )
    }


    private fun buildCalendarDays(
        yearMonth: YearMonth,
        statuses: List<DayPrayerStatus>
    ): List<CalendarDay> {
        val statusByDate = statuses.associateBy { it.date }
        val today = LocalDate.now()

        val firstOfMonth = yearMonth.atDay(1)
        // Sunday = 7 in ISO DayOfWeek; we want Sunday-first grid.
        val firstDayOffset = firstOfMonth.dayOfWeek.value % 7 // Sunday -> 0, Monday -> 1 ... Saturday -> 6

        val gridStart = firstOfMonth.minusDays(firstDayOffset.toLong())
        val totalCells = 42 // 6 rows x 7 days, stable grid height

        return (0 until totalCells).map { offset ->
            val date = gridStart.plusDays(offset.toLong())
            CalendarDay(
                date = date,
                isCurrentMonth = YearMonth.from(date) == yearMonth,
                isToday = date == today,
                status = statusByDate[date] ?: DayPrayerStatus(date = date)
            )
        }
    }

    fun onPreviousMonth() {
        currentYearMonth.update { it.minusMonths(1) }
    }

    fun onNextMonth() {
        currentYearMonth.update { it.plusMonths(1) }
    }

    fun onDaySelected(date: LocalDate) {
        selectedDate.update { date }
    }

    fun onTogglePrayer(date: LocalDate, prayer: PrayerName) {
        viewModelScope.launch {
            togglePrayerCompletion(date, prayer)
        }
    }
}