package com.sayatcode.perday_presentation.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.sayatcode.core.repository.PrayerTimesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AlarmManager алармы не переживают перезагрузку устройства — этот ресивер
 * подхватывает BOOT_COMPLETED, берёт сегодняшние времена намазов из репозитория
 * и заново планирует все алармы через scheduler.scheduleAll(...).
 *
 * onReceive синхронный, а getForToday() suspend — поэтому используем
 * goAsync() + CoroutineScope(Dispatchers.IO), чтобы system не убила процесс
 * до завершения корутины (есть ограничение ~10 сек на BroadcastReceiver).
 */
@AndroidEntryPoint
class PrayerBootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var scheduler: PrayerAlarmScheduler

    @Inject
    lateinit var repository: PrayerTimesRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val today = repository.getForToday()
                if (today != null) {
                    scheduler.scheduleAll(
                        fajr = today.fajr,
                        dhuhr = today.dhuhr,
                        asr = today.asr,
                        maghrib = today.maghrib,
                        isha = today.isha
                    )
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}