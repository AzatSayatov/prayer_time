package com.sayatcode.perday_presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sayatcode.perday_presentation.domain.PrayerPerDayViewModel
import com.sayatcode.perday_presentation.notification.PrayerAlarmScheduler
import com.sayatcode.perday_presentation.notification.PrayerAlarmType
import com.sayatcode.perday_presentation.notification.PrayerNotificationPrefs

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimesList(
    viewModel: PrayerPerDayViewModel = hiltViewModel(),
    onTimeClick:(index: Int) -> Unit,
    onRingClick:(index: Int) -> Unit
){
    val state = viewModel.prayerState
    val context = LocalContext.current

    val prefs = remember { PrayerNotificationPrefs(context) }
    val scheduler = remember { PrayerAlarmScheduler(context) }

    // Локальный снимок enabled-флагов, чтобы UI сразу перерисовывался при тапе по ring icon.
    var enabledMap by remember {
        mutableStateOf(PrayerAlarmType.entries.associateWith { prefs.isEnabled(it) })
    }

    fun toggle(type: PrayerAlarmType, timeHHmm: String) {
        val newValue = prefs.toggle(type)
        enabledMap = enabledMap.toMutableMap().apply { this[type] = newValue }
        if (newValue) {
            scheduler.scheduleAll(
                fajr = state.fajr,
                dhuhr = state.dhuhr,
                asr = state.asr,
                maghrib = state.magrib,
                isha = state.isha
            )
        } else {
            scheduler.cancel(type)
        }
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .padding(16.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        TimeItem(
            prayName = "Ertir",
            prayTime = state.fajr,
            notificationEnabled = enabledMap[PrayerAlarmType.FAJR] ?: true,
            onClick = {},
            onRingClick = {
                toggle(PrayerAlarmType.FAJR,state.fajr)
            }
        )
        TimeItem(
            prayName = "Gun",
            prayTime = state.sunrice,
            onClick = {},
            onRingClick = {
//                toggle(PrayerAlarmType.FAJR,state.fajr)
            }
        )
        TimeItem(
            prayName = "Oyle",
            prayTime = state.dhuhr,
            notificationEnabled = enabledMap[PrayerAlarmType.DHUHR] ?: true,
            onClick = {},
            onRingClick = {
                toggle(PrayerAlarmType.DHUHR,state.dhuhr)
            }
        )
        TimeItem(
            prayName = "Ikindi",
            prayTime = state.asr,
            notificationEnabled = enabledMap[PrayerAlarmType.ASR] ?: true,
            onClick = {},
            onRingClick = {
                toggle(PrayerAlarmType.ASR,state.asr)
            }
        )
        TimeItem(
            prayName = "Agsham",
            prayTime = state.magrib,
            notificationEnabled = enabledMap[PrayerAlarmType.MAGHRIB] ?: true,
            onClick = {},
            onRingClick = {
                toggle(PrayerAlarmType.MAGHRIB,state.magrib)
            }
        )
        TimeItem(
            prayName = "Yassy",
            prayTime = state.isha,
            notificationEnabled = enabledMap[PrayerAlarmType.ISHA] ?: true,
            onClick = {},
            onRingClick = {
                toggle(PrayerAlarmType.ISHA,state.isha)
            }
        )
    }
}