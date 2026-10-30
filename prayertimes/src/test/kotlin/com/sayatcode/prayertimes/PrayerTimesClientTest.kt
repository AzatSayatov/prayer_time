package com.sayatcode.prayertimes

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class PrayerTimesClientTest {
    private val client = PrayerTimesClient()

    @Test
    fun turkmenistan_usesTableAndDerivesRest() {
        val t = client.turkmenistan(LocalDate.of(2025, 1, 1))
        assertEquals(PrayerSource.TURKMENISTAN_OFFICIAL, t.source)
        assertEquals(LocalTime.of(7, 12), t.fajr)
        assertEquals(LocalTime.of(8, 22), t.sunrise)
        assertEquals(LocalTime.of(13, 30), t.dhuhr)
        assertEquals(LocalTime.of(16, 30), t.asr)
        assertEquals(LocalTime.of(18, 10), t.maghrib)
        assertEquals(LocalTime.of(19, 30), t.isha)
    }

    @Test
    fun turkmenistan_hasEveryDayOfLeapYear() {
        var d = LocalDate.of(2024, 1, 1)
        while (d.year == 2024) { client.turkmenistan(d); d = d.plusDays(1) }
    }

    @Test
    fun worldwide_isMarkedAsCalculatedAndOrdered() {
        val loc = Location(51.5074, -0.1278, ZoneId.of("Europe/London"))
        val t = client.worldwide(loc, LocalDate.of(2025, 6, 21))
        assertEquals(PrayerSource.WORLDWIDE_CALCULATED, t.source)
        assertTrue(t.sunrise < t.dhuhr && t.dhuhr < t.asr && t.asr < t.maghrib)
    }

    @Test(expected = IllegalArgumentException::class)
    fun location_rejectsBadLatitude() { Location(120.0, 0.0) }
}
