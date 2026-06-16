package com.sayatcode.perday_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sayatcode.perday_presentation.domain.NextPrayName
import com.sayatcode.perday_presentation.domain.PrayerGradients
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Один намаз с именем и временем в виде строки "HH:mm".
 */
data class PrayerTimeEntry(
    val name: NextPrayName,
    val time: String
)

/**
 * Результат поиска следующего намаза: его имя и оставшееся время.
 */
private data class NextPrayerInfo(
    val name: NextPrayName,
    val remaining: Duration
)

private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

/**
 * Безопасный парсинг "HH:mm" — возвращает null вместо краша,
 * если строка пустая или некорректная (например, до загрузки данных в ViewModel).
 */
private fun parseTimeOrNull(time: String): LocalTime? =
    try {
        LocalTime.parse(time, timeFormatter)
    } catch (e: Exception) {
        null
    }

/**
 * Находит следующий намаз относительно текущего момента.
 * Если все намазы на сегодня уже прошли — берёт Fajr следующего дня (24ч + время Fajr).
 * Возвращает null, если данные ещё не загружены (пустые/невалидные строки времени).
 */
private fun findNextPrayer(
    prayers: List<PrayerTimeEntry>,
    now: LocalDateTime
): NextPrayerInfo? {
    val today = now.toLocalDate()

    val todayDateTimes = prayers.mapNotNull { entry ->
        parseTimeOrNull(entry.time)?.let { parsed -> entry to LocalDateTime.of(today, parsed) }
    }

    if (todayDateTimes.isEmpty()) return null

    val upcoming = todayDateTimes
        .filter { (_, dateTime) -> dateTime.isAfter(now) }
        .minByOrNull { (_, dateTime) -> dateTime }

    if (upcoming != null) {
        val (entry, dateTime) = upcoming
        return NextPrayerInfo(entry.name, Duration.between(now, dateTime))
    }

    // Все намазы сегодня прошли — следующий это Fajr завтра
    val fajrEntry = prayers.firstOrNull { it.name == NextPrayName.FAJR } ?: return null
    val fajrTime = parseTimeOrNull(fajrEntry.time) ?: return null
    val tomorrowFajr = LocalDateTime.of(today.plusDays(1), fajrTime)
    return NextPrayerInfo(fajrEntry.name, Duration.between(now, tomorrowFajr))
}

private fun Duration.toCountdownString(): String {
    val totalSeconds = this.seconds.coerceAtLeast(0)
    val h = totalSeconds / 3600
    val m = (totalSeconds % 3600) / 60
    val s = totalSeconds % 60
    return "%02d:%02d:%02d".format(h, m, s)
}

@Composable
fun NextPray(
    fajr: String,
    sunrise: String,
    dhuhr: String,
    asr: String,
    maghrib: String,
    isha: String,
    today: LocalDate = LocalDate.now()
) {
    val prayers = remember(fajr, sunrise, dhuhr, asr, maghrib, isha) {
        listOf(
            PrayerTimeEntry(NextPrayName.FAJR, fajr),
            PrayerTimeEntry(NextPrayName.SUNRISE, sunrise),
            PrayerTimeEntry(NextPrayName.DHUHR, dhuhr),
            PrayerTimeEntry(NextPrayName.ASR, asr),
            PrayerTimeEntry(NextPrayName.MAGHRIB, maghrib),
            PrayerTimeEntry(NextPrayName.ISHA, isha)
        )
    }

    var nextPrayer by remember { mutableStateOf<NextPrayerInfo?>(findNextPrayer(prayers, LocalDateTime.now())) }

    // Живой тикающий таймер: пересчитываем каждую секунду.
    androidx.compose.runtime.LaunchedEffect(prayers) {
        while (true) {
            nextPrayer = findNextPrayer(prayers, LocalDateTime.now())
            delay(1000)
        }
    }

    val gradient = nextPrayer?.let { PrayerGradients.forPrayer(it.name) } ?: PrayerGradients.Dhuhr

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
            .background(
                gradient,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text(
                text = nextPrayer?.let { PrayerGradients.displayName(it.name) } ?: "...",
                fontWeight = FontWeight.Bold
            )
            Text(text = "Galan wagty")
            Text(
                text = nextPrayer?.remaining?.toCountdownString() ?: "--:--:--",
                fontWeight = FontWeight.Bold
            )
        }
    }
}