package com.sayatcode.calendar_data.di


import com.sayatcode.calendar_data.repository.PrayerCompletionRepositoryImpl
import com.sayatcode.calendar_domain.repository.PrayerCompletionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CalendarDataModule {

    @Binds
    @Singleton
    abstract fun bindPrayerCompletionRepository(
        impl: PrayerCompletionRepositoryImpl
    ): PrayerCompletionRepository
}