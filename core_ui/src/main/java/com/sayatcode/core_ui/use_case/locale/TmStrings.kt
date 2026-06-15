package com.sayatcode.core_ui.use_case.locale

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locale.TM,true)
val TmStrings = Strings(
    january = "",
    hijriString = {day, month, year -> "$day, ${hijriMonthsEn[month - 1]} $year"},
)

val hijriMonthsTk = listOf(
    "Aşyr (Muharram)", "Sapar (Safar)", "4 Tirkeşik 1 (Rabu'ul Awwal)",
    "4 Tirkeşik 2 (Rabi'ul Akhir)",
    "4 Tirkeşik 3 (Jumadal Ula)", "4 Tirkeşik 4 (Jumadal Akhira)",
    "Rejep (Rajab)", "Meret (Sha'ban)",
    "Oraza (Ramadan)", "Baýram (Shawwal)",
    "Boş aý (Dhu al-Qi'dah) ", "Gurban (Dhu al-Hijjah)"
)