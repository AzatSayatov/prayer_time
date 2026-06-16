package com.sayatcode.perday_presentation.domain

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object PrayerGradients {
    // Fajr (рассвет) — глубокий сине-фиолетовый переходящий в розовый горизонт
    val Fajr = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0D1B3E), // тёмно-синее ночное небо
            Color(0xFF3A2D6E), // переход
            Color(0xFF8E5B9F), // фиолетово-розовый
            Color(0xFFE8946B)  // тёплый край рассвета
        )
    )
    // Sunrise (восход) — похож на Fajr, но светлее и теплее
    val Sunrise = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF2A4D7A),
            Color(0xFF6A6FA0),
            Color(0xFFE2A06B),
            Color(0xFFF5D38A)
        )
    )


    // Dhuhr (полдень) — яркое голубое небо, высокое солнце
    val Dhuhr = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1E88C7), // насыщенный голубой
            Color(0xFF53B6E8), // светлее
            Color(0xFFA9E0F5), // почти белёсый горизонт
            Color(0xFFE9F7FB)  // лёгкая дымка
        )
    )

    // Asr (послеполуденный) — золотисто-жёлтый, тёплый дневной свет на закат
    val Asr = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1B6FA8), // верх небо ещё голубое
            Color(0xFF4FA3C9),
            Color(0xFFF2C75C), // золотистое солнце
            Color(0xFFF7A65A)  // тёплый низ
        )
    )

    // Maghrib (закат) — насыщенный красно-оранжевый закат
    val Maghrib = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1A1640), // тёмное верхнее небо
            Color(0xFF6B2E5F), // переход
            Color(0xFFD1493C), // огненно-красный
            Color(0xFFF4A93B)  // оранжевый горизонт
        )
    )

    // Isha (ночь) — тёмно-синий/чёрный с лёгким сиянием на горизонте
    val Isha = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF02030D), // почти чёрный
            Color(0xFF0A1130),
            Color(0xFF161F4D),
            Color(0xFF2B3A73)  // лёгкое синее свечение внизу
        )
    )

    fun forPrayer(prayer: NextPrayName): Brush = when (prayer) {
        NextPrayName.FAJR -> Fajr
        NextPrayName.SUNRISE -> Sunrise
        NextPrayName.DHUHR -> Dhuhr
        NextPrayName.ASR -> Asr
        NextPrayName.MAGHRIB -> Maghrib
        NextPrayName.ISHA -> Isha
    }
    fun displayName(prayer: NextPrayName): String = when (prayer) {
        NextPrayName.FAJR -> "Ertir"
        NextPrayName.SUNRISE -> "Gün"
        NextPrayName.DHUHR -> "Öýle"
        NextPrayName.ASR -> "Ikindi"
        NextPrayName.MAGHRIB -> "Agşam"
        NextPrayName.ISHA -> "Ýassy"
    }


}