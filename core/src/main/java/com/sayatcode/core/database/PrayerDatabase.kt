package com.sayatcode.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sayatcode.core.database.dao.PrayerTimesDao
import com.sayatcode.core.database.entity.DailyPrayerTimesEntity

@Database(entities = [DailyPrayerTimesEntity::class], version = 1)
abstract class PrayerDatabase : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao
}