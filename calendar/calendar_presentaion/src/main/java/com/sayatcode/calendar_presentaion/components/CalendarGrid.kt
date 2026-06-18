package com.sayatcode.calendar_presentaion.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sayatcode.calendar_presentaion.model.CalendarDay
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    days: List<CalendarDay>,
    selectedDate: LocalDate?,
    onDayClick: (LocalDate) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        days.chunked(7).forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { day ->
                    CalendarDayItem(
                        day = day,
                        isSelected = day.date == selectedDate,
                        onClick = { onDayClick(day.date) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}