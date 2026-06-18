package com.sayatcode.calendar_presentaion.ui


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale
import com.sayatcode.calendar_presentaion.components.CalendarDayItem
import com.sayatcode.calendar_presentaion.components.CalendarGrid
import com.sayatcode.calendar_presentaion.components.PrayerChecklist
import com.sayatcode.calendar_presentaion.model.selectedDayStatus

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxWidth()
        .verticalScroll(scrollState)) {
        MonthNavigationHeader(
            monthLabel = "${state.yearMonth.month.getDisplayName(TextStyle.FULL, LocalLocale.current.platformLocale)} ${state.yearMonth.year}",
            onPreviousClick = viewModel::onPreviousMonth,
            onNextClick = viewModel::onNextMonth
        )

        WeekdayHeaderRow()

        CalendarGrid(
            days = state.days,
            selectedDate = state.selectedDate
        ) { selectedDate ->
            viewModel.onDaySelected(date = selectedDate)
        }

//        LazyVerticalGrid(
//            columns = GridCells.Fixed(7),
//            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
//        ) {
//            items(state.days) { day ->
//                CalendarDayItem(
//                    day = day,
//                    isSelected = day.date == state.selectedDate,
//                    onClick = { viewModel.onDaySelected(day.date) }
//                )
//            }
//        }

        PrayerChecklist(
            dayStatus = state.selectedDayStatus,
            onTogglePrayer = { prayer ->
                state.selectedDate?.let { date ->
                    viewModel.onTogglePrayer(date, prayer)
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun MonthNavigationHeader(
    monthLabel: String,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous month")
        }
        Text(text = monthLabel, style = MaterialTheme.typography.button)
        IconButton(onClick = onNextClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next month")
        }
    }
}

@Composable
private fun WeekdayHeaderRow() {
    val weekdayLabels = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(modifier = Modifier.fillMaxWidth()) {
        weekdayLabels.forEach { label ->
            Text(
                text = label,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
        }
    }
}