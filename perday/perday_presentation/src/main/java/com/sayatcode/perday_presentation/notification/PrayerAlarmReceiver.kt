package com.sayatcode.perday_presentation.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.sayatcode.core.repository.PrayerTimesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * Срабатывает в момент времени намаза (через AlarmManager.setExactAndAllowWhileIdle).
 * Проверяет, не отключено ли уведомление для этого намаза (per-namaz toggle),
 * и если включено — показывает уведомление, а затем сразу планирует следующее
 * срабатывание этого же намаза на завтра (т.к. exact alarms не повторяются сами).
 *
 * Время на завтра берётся из репозитория (а не "то же HH:mm + 1 день"), т.к.
 * расписание у тебя по дате (день-месяц) и время намаза каждый день немного сдвигается.
 */
@AndroidEntryPoint
class PrayerAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var prefs: PrayerNotificationPrefs

    @Inject
    lateinit var scheduler: PrayerAlarmScheduler

    @Inject
    lateinit var repository: PrayerTimesRepository

    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val requestCode = intent.getIntExtra(EXTRA_REQUEST_CODE, -1)
        val type = PrayerAlarmType.fromRequestCode(requestCode) ?: return

        if (prefs.isEnabled(type)) {
            PrayerNotificationHelper.showPrayerNotification(context, type)
        }

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tomorrow = LocalDate.now().plusDays(1)
                val tomorrowTimes = repository.getForDate(tomorrow.dayOfMonth, tomorrow.monthValue)
                val timeHHmm = when (type) {
                    PrayerAlarmType.FAJR -> tomorrowTimes?.fajr
                    PrayerAlarmType.DHUHR -> tomorrowTimes?.dhuhr
                    PrayerAlarmType.ASR -> tomorrowTimes?.asr
                    PrayerAlarmType.MAGHRIB -> tomorrowTimes?.maghrib
                    PrayerAlarmType.ISHA -> tomorrowTimes?.isha
                }
                if (timeHHmm != null) {
                    scheduler.scheduleNextDay(type, timeHHmm)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val EXTRA_REQUEST_CODE = "extra_request_code"
    }
}