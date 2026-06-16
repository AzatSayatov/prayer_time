package com.sayatcode.perday_presentation.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sayatcode.core_ui.LocalStrings
import com.sayatcode.perday_presentation.components.NextPrayWithToggle
import com.sayatcode.perday_presentation.components.TimesList
import com.sayatcode.perday_presentation.domain.PrayerPerDayViewModel
import com.sayatcode.perday_presentation.notification.PrayerAlarmScheduler

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrayerTimeOfDayScreen(
    viewModel: PrayerPerDayViewModel = hiltViewModel()
) {
    val strings = LocalStrings.current
    val state = viewModel.prayerState
    val (day, month, year) = state.hijriDate
    val context = LocalContext.current
    val scheduler = remember { PrayerAlarmScheduler(context) }

    // Перепланируем все алармы каждый раз, когда приходят новые/обновлённые
    // времена намазов (например, после первой загрузки из БД/сети).
    LaunchedEffect(state.fajr, state.dhuhr, state.asr, state.magrib, state.isha) {
        scheduler.scheduleAll(
            fajr = state.fajr,
            dhuhr = state.dhuhr,
            asr = state.asr,
            maghrib = state.magrib,
            isha = state.isha
        )
    }
    LaunchedEffect(state.hijriDate) {
        Log.d("HijriDate", "PrayerTimeOfDayScreen: ${state.hijriDate.first}, ${state.hijriDate.second}, ${state.hijriDate.third}")
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NextPrayWithToggle(
            fajr = state.fajr,
            sunrise = state.sunrice,
            dhuhr = state.dhuhr,
            asr = state.asr,
            maghrib = state.magrib,
            isha = state.isha,
            onPreviousClick = viewModel::OnPreviousClickForTm,
            onNextClick = viewModel::OnNextClickForTm,
            miladyDate = "${state.date.dayOfMonth}.${state.date.monthValue}.${state.date.year}",
            hijriDate = strings.hijriString(
                state.hijriDate.first,
                state.hijriDate.second,
                state.hijriDate.third)
        )

//        Text(text = "Ertir ${state.fajr}")
//        Text(text = "Gun ${state.sunrice}")
//        Text(text = "Oyle ${state.dhuhr}")
//        Text(text = "Ikindi ${state.asr}")
//        Text(text = "agsam ${state.magrib}")
//        Text(text = "yassy ${state.isha}")
        TimesList(
            onTimeClick = {i -> },
            onRingClick = { i -> }
        )
    }
}