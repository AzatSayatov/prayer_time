package com.sayatcode.core_ui.use_case.locale

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locale.RU,false)
val RuStrings = Strings(
    january = "",
    hijriString = {day, month, year -> "$day, ${hijriMonthsRu[month - 1]} $year"},
)

val hijriMonthsRu = listOf(
    "Мухаррам", "Сафар", "Раби аль-Авваль", "Раби аль-Ахир",
    "Джумада аль-Уля", "Джумада аль-Ахира", "Раджаб", "Шаабан",
    "Рамадан", "Шавваль", "Зуль-Каада", "Зуль-Хиджа"
)