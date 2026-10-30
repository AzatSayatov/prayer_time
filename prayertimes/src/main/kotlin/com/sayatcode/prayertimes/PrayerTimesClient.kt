package com.sayatcode.prayertimes

import com.batoulapps.adhan2.Coordinates
import com.batoulapps.adhan2.Madhab
import com.batoulapps.adhan2.data.DateComponents
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.batoulapps.adhan2.CalculationMethod as AdhanMethod
import com.batoulapps.adhan2.PrayerTimes as AdhanPrayerTimes

/**
 * Entry point of the library. Two explicit sources, two methods:
 *
 * ```
 * val client = PrayerTimesClient()
 *
 * // 1. Turkmenistan table (no coordinates needed)
 * val tm = client.turkmenistan(LocalDate.now())
 *
 * // 2. Anywhere in the world (calculated from coordinates)
 * val london = client.worldwide(Location(51.5, -0.12, ZoneId.of("Europe/London")), LocalDate.now())
 *
 * tm.source      // TURKMENISTAN_OFFICIAL
 * london.source  // WORLDWIDE_CALCULATED
 * ```
 */
@OptIn(kotlin.time.ExperimentalTime::class)
public class PrayerTimesClient {

    private val turkmenistanTable: Map<String, TableRow> by lazy { TurkmenistanTable.load() }

    /**
     * Times from the bundled Turkmenistan table.
     *
     * Only fajr and asr are stored in the table; the rest is derived:
     * sunrise = fajr + 70 min, dhuhr = 13:30, maghrib = asr + 100 min, isha = asr + 180 min.
     */
    public fun turkmenistan(date: LocalDate): PrayerTimes {
        val row = turkmenistanTable["${date.dayOfMonth}-${date.monthValue}"]
            ?: error("No Turkmenistan data for $date")
        return PrayerTimes(
            date = date,
            fajr = row.fajr,
            sunrise = row.fajr.plusMinutes(70),
            dhuhr = LocalTime.of(13, 30),
            asr = row.asr,
            maghrib = row.asr.plusMinutes(100),
            isha = row.asr.plusMinutes(180),
            source = PrayerSource.TURKMENISTAN_OFFICIAL,
            timeZone = TURKMENISTAN_ZONE
        )
    }

    /** Times calculated for any [location] on Earth. */
    public fun worldwide(
        location: Location,
        date: LocalDate,
        options: CalculationOptions = CalculationOptions()
    ): PrayerTimes {
        val params = options.method.toAdhan().parameters.copy(
            madhab = if (options.asrSchool == AsrSchool.HANAFI) Madhab.HANAFI else Madhab.SHAFI
        )
        val adhan = AdhanPrayerTimes(
            Coordinates(location.latitude, location.longitude),
            DateComponents(date.year, date.monthValue, date.dayOfMonth),
            params
        )
        fun toLocal(millis: Long): LocalTime =
            Instant.ofEpochMilli(millis).atZone(location.timeZone).toLocalTime().withSecond(0).withNano(0)

        return PrayerTimes(
            date = date,
            fajr = toLocal(adhan.fajr.toEpochMilliseconds()),
            sunrise = toLocal(adhan.sunrise.toEpochMilliseconds()),
            dhuhr = toLocal(adhan.dhuhr.toEpochMilliseconds()),
            asr = toLocal(adhan.asr.toEpochMilliseconds()),
            maghrib = toLocal(adhan.maghrib.toEpochMilliseconds()),
            isha = toLocal(adhan.isha.toEpochMilliseconds()),
            source = PrayerSource.WORLDWIDE_CALCULATED,
            timeZone = location.timeZone
        )
    }

    private fun CalculationMethod.toAdhan(): AdhanMethod = when (this) {
        CalculationMethod.MUSLIM_WORLD_LEAGUE -> AdhanMethod.MUSLIM_WORLD_LEAGUE
        CalculationMethod.EGYPTIAN -> AdhanMethod.EGYPTIAN
        CalculationMethod.KARACHI -> AdhanMethod.KARACHI
        CalculationMethod.UMM_AL_QURA -> AdhanMethod.UMM_AL_QURA
        CalculationMethod.DUBAI -> AdhanMethod.DUBAI
        CalculationMethod.QATAR -> AdhanMethod.QATAR
        CalculationMethod.KUWAIT -> AdhanMethod.KUWAIT
        CalculationMethod.SINGAPORE -> AdhanMethod.SINGAPORE
        CalculationMethod.TURKEY -> AdhanMethod.TURKEY
        CalculationMethod.NORTH_AMERICA -> AdhanMethod.NORTH_AMERICA
    }

    private companion object {
        val TURKMENISTAN_ZONE: ZoneId = ZoneId.of("Asia/Ashgabat")
    }
}

internal data class TableRow(val fajr: LocalTime, val asr: LocalTime)

internal object TurkmenistanTable {
    private val fmt = DateTimeFormatter.ofPattern("HH:mm")

    @kotlinx.serialization.Serializable
    private data class Row(val date: String, val fajr: String, val asr: String)

    @kotlinx.serialization.Serializable
    private data class Root(val prayer_times: List<Row>)

    fun load(): Map<String, TableRow> {
        val text = checkNotNull(TurkmenistanTable::class.java.getResourceAsStream("/prayertimes/turkmenistan.json")) {
            "turkmenistan.json is missing from the library"
        }.bufferedReader().use { it.readText() }
        return kotlinx.serialization.json.Json.decodeFromString<Root>(text).prayer_times
            .associate { it.date to TableRow(LocalTime.parse(it.fajr, fmt), LocalTime.parse(it.asr, fmt)) }
    }
}
