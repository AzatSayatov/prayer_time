package com.sayatcode.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sayatcode.core.database.entity.DailyPrayerTimesEntity

@Dao
interface PrayerTimesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(times: List<DailyPrayerTimesEntity>)

    @Query("SELECT * FROM daily_prayer_times WHERE date = :date")
    suspend fun getByDate(date: String): DailyPrayerTimesEntity?

    @Query("SELECT COUNT(*) FROM daily_prayer_times")
    suspend fun getCount(): Int
}