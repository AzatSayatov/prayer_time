package com.sayatcode.core.repository

import android.content.Context
import com.sayatcode.core.database.dao.PrayerTimesDao
import com.sayatcode.core.database.entity.DailyPrayerTimesEntity
import com.sayatcode.core.database.entity.toDomain
import com.sayatcode.core.model.DailyPrayerTimes
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Singleton
import org.json.JSONObject
import java.time.LocalDate
import javax.inject.Inject

@Singleton
class PrayerTimesRepository @Inject constructor(
    private val dao: PrayerTimesDao,
    @ApplicationContext private val context: Context
) {
    suspend fun seedIfNeeded() {
        if (dao.getCount() > 0) return

        val json = context.assets.open("prayer_times.json")
            .bufferedReader().readText()

        val jsonArray = JSONObject(json).getJSONArray("prayer_times")

        val entities = (0 until jsonArray.length()).map { i ->
            val obj = jsonArray.getJSONObject(i)
            DailyPrayerTimesEntity(
                date = obj.getString("date"),
                fajr = obj.getString("fajr"),
                asr = obj.getString("asr")
            )
        }

        dao.insertAll(entities)
    }

    suspend fun getForToday(): DailyPrayerTimes? {
        val now = LocalDate.now()
        val key = "${now.dayOfMonth}-${now.monthValue}"
        return dao.getByDate(key)?.toDomain()
    }

    suspend fun getForDate(day: Int, month: Int): DailyPrayerTimes? {
        val key = "$day-$month"
        return dao.getByDate(key)?.toDomain()
    }
}