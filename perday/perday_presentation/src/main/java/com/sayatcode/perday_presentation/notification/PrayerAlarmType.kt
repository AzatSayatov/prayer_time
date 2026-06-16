package com.sayatcode.perday_presentation.notification

/**
 * Идентификатор намаза для системы уведомлений/алармов.
 * requestCode используется как уникальный PendingIntent request code
 * и как ключ в SharedPreferences для toggle "включено/выключено".
 */
enum class PrayerAlarmType(val requestCode: Int, val displayName: String) {
    FAJR(1001, "Ertir"),
    DHUHR(1002, "Öýle"),
    ASR(1003, "Ikindi"),
    MAGHRIB(1004, "Agşam"),
    ISHA(1005, "Ýassy");

    companion object {
        fun fromRequestCode(code: Int): PrayerAlarmType? = entries.find { it.requestCode == code }
    }
}