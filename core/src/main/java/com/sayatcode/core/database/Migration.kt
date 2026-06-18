package com.sayatcode.core.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `prayer_completion` (
                `date` TEXT NOT NULL,
                `fajr` INTEGER NOT NULL DEFAULT 0,
                `dhuhr` INTEGER NOT NULL DEFAULT 0,
                `asr` INTEGER NOT NULL DEFAULT 0,
                `maghrib` INTEGER NOT NULL DEFAULT 0,
                `isha` INTEGER NOT NULL DEFAULT 0,
                PRIMARY KEY(`date`)
            )
            """.trimIndent()
        )
    }
}