package com.sayatcode.calendar_data.local.dao


import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sayatcode.calendar_data.local.entity.PrayerCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerCompletionDao {

    @Query("SELECT * FROM prayer_completion WHERE date BETWEEN :startDate AND :endDate")
    fun observeRange(startDate: String, endDate: String): Flow<List<PrayerCompletionEntity>>

    @Query("SELECT * FROM prayer_completion WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): PrayerCompletionEntity?

    @Upsert
    suspend fun upsert(entity: PrayerCompletionEntity)
}