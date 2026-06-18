package com.sayatcode.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sayatcode.calendar_data.local.dao.PrayerCompletionDao
import com.sayatcode.calendar_data.local.entity.PrayerCompletionEntity
import com.sayatcode.core.database.dao.PrayerTimesDao
import com.sayatcode.core.database.entity.DailyPrayerTimesEntity

@Database(entities = [
    DailyPrayerTimesEntity::class,
    PrayerCompletionEntity::class
                     ],
    version = 2)
abstract class PrayerDatabase : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao
    abstract fun prayerCompletionDao(): PrayerCompletionDao
}