package com.sayatcode.core.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.sayatcode.core.database.PrayerDatabase
import com.sayatcode.core.database.dao.PrayerTimesDao
import com.sayatcode.core.preferenes.Preferences
import com.sayatcode.core.repository.PrayerTimesRepository
import com.sayatcode.core.use_case.DefaultPreferences
import com.sayatcode.core.use_case.GetPrayerInfoForDate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideGetPrayerInfoForDateUseCase(): GetPrayerInfoForDate {
        return GetPrayerInfoForDate()
    }

    @Provides
    @Singleton
    fun providePrayerTimesRepository(
        app: Application,
        db: PrayerDatabase
    ): PrayerTimesRepository{
        return PrayerTimesRepository(db.prayerTimesDao(),app)
    }
    @Provides
    @Singleton
    fun providePrayerDatabase(app: Application) : PrayerDatabase{
        return Room.databaseBuilder(
            app,
            PrayerDatabase::class.java,
            "prayer_db"
        ).build()
    }
    @Provides
    @Singleton
    fun providePrayerTimesDao(db: PrayerDatabase): PrayerTimesDao {
        return db.prayerTimesDao()
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences{
        return app.getSharedPreferences("shared_pref", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences)
    }
}