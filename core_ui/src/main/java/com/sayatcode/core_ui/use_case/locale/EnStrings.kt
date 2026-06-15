package com.sayatcode.core_ui.use_case.locale

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locale.EN,false)
val EnStrings = Strings(
    january = "",
    hijriString = { day, month, year -> "$day, ${hijriMonthsEn[month - 1]} $year" },
)
val hijriMonthsEn = listOf(
    "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani",
    "Jumada al-Ula", "Jumada al-Thani", "Rajab", "Sha'ban",
    "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
)