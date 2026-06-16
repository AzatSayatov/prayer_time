package com.sayatcode.perday_presentation.notification

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Хранит per-намаз состояние "уведомление включено/выключено".
 * По умолчанию все уведомления включены.
 */
@Singleton
class PrayerNotificationPrefs @Inject constructor(
    context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isEnabled(type: PrayerAlarmType): Boolean =
        prefs.getBoolean(key(type), true)

    fun setEnabled(type: PrayerAlarmType, enabled: Boolean) {
        prefs.edit().putBoolean(key(type), enabled).apply()
    }

    fun toggle(type: PrayerAlarmType): Boolean {
        val newValue = !isEnabled(type)
        setEnabled(type, newValue)
        return newValue
    }

    private fun key(type: PrayerAlarmType) = "notif_enabled_${type.name}"

    companion object {
        private const val PREFS_NAME = "prayer_notification_prefs"
    }
}