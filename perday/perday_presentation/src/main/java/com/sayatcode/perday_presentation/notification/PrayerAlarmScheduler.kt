package com.sayatcode.perday_presentation.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Планирует точные срабатывания (AlarmManager.setExactAndAllowWhileIdle) для
 * каждого намаза на сегодня/завтра. Intent несёт только requestCode намаза —
 * PrayerAlarmReceiver сам спрашивает PrayerTimesRepository за точное время,
 * т.к. расписание зависит от даты и время каждый день немного сдвигается.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Singleton
class PrayerAlarmScheduler @Inject constructor(
    private val context: Context
) {
    private val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    /**
     * Планирует все 5 намазов на основе строк "HH:mm" из state.
     * Если время на сегодня уже прошло — планирует на завтра.
     * Вызывай при каждом обновлении state (новые данные с сервера/из БД),
     * чтобы алармы не устарели.
     */
    fun scheduleAll(
        fajr: String,
        dhuhr: String,
        asr: String,
        maghrib: String,
        isha: String
    ) {
        scheduleForTimeString(PrayerAlarmType.FAJR, fajr)
        scheduleForTimeString(PrayerAlarmType.DHUHR, dhuhr)
        scheduleForTimeString(PrayerAlarmType.ASR, asr)
        scheduleForTimeString(PrayerAlarmType.MAGHRIB, maghrib)
        scheduleForTimeString(PrayerAlarmType.ISHA, isha)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleForTimeString(type: PrayerAlarmType, timeHHmm: String) {
        val parsedTime = parseTimeOrNull(timeHHmm) ?: return
        val now = LocalDateTime.now()
        var target = LocalDateTime.of(now.toLocalDate(), parsedTime)
        if (target.isBefore(now)) {
            target = target.plusDays(1)
        }
        scheduleExact(type, target)
    }

    /** Используется ресивером, чтобы сразу перепланировать тот же намаз на следующий день. */
    fun scheduleNextDay(type: PrayerAlarmType, timeHHmm: String) {
        val parsedTime = parseTimeOrNull(timeHHmm) ?: return
        val target = LocalDateTime.of(LocalDate.now().plusDays(1), parsedTime)
        scheduleExact(type, target)
    }

    private fun parseTimeOrNull(time: String): LocalTime? =
        try {
            LocalTime.parse(time, timeFormatter)
        } catch (e: Exception) {
            null
        }

    private fun scheduleExact(type: PrayerAlarmType, target: LocalDateTime) {
        val triggerAtMillis = target.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
            putExtra(PrayerAlarmReceiver.EXTRA_REQUEST_CODE, type.requestCode)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            type.requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            // Нет разрешения SCHEDULE_EXACT_ALARM — пропускаем, экран настроек
            // должен направить пользователя в системные настройки (см. requestExactAlarmPermissionIfNeeded).
            return
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    /** Отменяет запланированный аларм для конкретного намаза (например, при выключении toggle). */
    fun cancel(type: PrayerAlarmType) {
        val intent = Intent(context, PrayerAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            type.requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    /** true, если на Android 12+ ещё не выдано разрешение на точные алармы. */
    fun needsExactAlarmPermission(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()
}