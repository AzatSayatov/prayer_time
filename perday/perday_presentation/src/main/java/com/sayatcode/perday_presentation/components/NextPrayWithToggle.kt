package com.sayatcode.perday_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Композиция NextPray + ToggleDay, где ToggleDay приподнят вверх
 * так, что его верхняя половина перекрывает нижний край NextPray.
 *
 * Высота ToggleDay фиксируется через toggleHeight, половина которой (toggleHeight / 2)
 * используется как отрицательный отступ, поднимающий его над нижней границей NextPray.
 */
@Composable
fun NextPrayWithToggle(
    fajr: String,
    sunrise: String,
    dhuhr: String,
    asr: String,
    maghrib: String,
    isha: String,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    miladyDate: String,
    hijriDate: String,
    toggleHeight: androidx.compose.ui.unit.Dp = 64.dp
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        NextPray(
            fajr = fajr,
            sunrise = sunrise,
            dhuhr = dhuhr,
            asr = asr,
            maghrib = maghrib,
            isha = isha
        )

        // Контейнер высотой в половину ToggleDay — это "выделяет" место,
        // чтобы следующий элемент в Column не перекрыл сам ToggleDay,
        // а ToggleDay внутри сдвинут вверх на полную свою высоту через offset,
        // из-за чего ровно половина оказывается над NextPray, половина — под ним.
        Box(modifier = Modifier.fillMaxWidth()) {
            ToggleDay(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = -(toggleHeight / 2)),
                onPreviousClick = onPreviousClick,
                onNextClick = onNextClick,
                miladyDate = miladyDate,
                hijriDate = hijriDate
            )
            Box(modifier = Modifier.height(toggleHeight / 2))
        }
    }
}