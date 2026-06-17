package com.sayatcode.calendar_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "prayer_completion")
data class PrayerCompletionEntity(
    @PrimaryKey
    val date: String, // ISO_LOCAL_DATE, e.g. "2026-06-17"
    val fajr: Boolean = false,
    val dhuhr: Boolean = false,
    val asr: Boolean = false,
    val maghrib: Boolean = false,
    val isha: Boolean = false
)