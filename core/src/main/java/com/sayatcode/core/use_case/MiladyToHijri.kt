package com.sayatcode.core.use_case

import android.icu.util.IslamicCalendar
import com.batoulapps.adhan2.data.DateComponents
import java.time.LocalDate
import java.util.GregorianCalendar

fun LocalDate.toHijri(): Triple<Int, Int, Int> {
    val gregorian = GregorianCalendar(this.year, this.monthValue - 1, this.dayOfMonth)
    val islamic = IslamicCalendar()
    islamic.time = gregorian.time  // konvertiruem cherez Date

    val day = islamic.get(IslamicCalendar.DAY_OF_MONTH)
    val month = islamic.get(IslamicCalendar.MONTH) + 1
    val year = islamic.get(IslamicCalendar.YEAR)

    return Triple(day, month, year)
}

val hijriMonths = listOf(
    "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani",
    "Jumada al-Ula", "Jumada al-Thani", "Rajab", "Sha'ban",
    "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
)
fun LocalDate.toHijriString(): String {
    val (day, month, year) = this.toHijri()
    return "$day, ${hijriMonths[month - 1]} $year"
}