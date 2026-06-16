package com.sayatcode.perday_presentation.domain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayatcode.core.model.PrayerInput
import com.sayatcode.core.preferenes.Preferences
import com.sayatcode.core.repository.PrayerTimesRepository
import com.sayatcode.core.use_case.GetPrayerInfoForDate
import com.sayatcode.core.use_case.toHijri
import com.sayatcode.core.use_case.toHijriString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PrayerPerDayViewModel @Inject constructor(
    private val getCurrentPrayerInfo: GetPrayerInfoForDate,
    private val prayerRepo: PrayerTimesRepository,
    private val preferences: Preferences
) : ViewModel() {


    var prayerState by mutableStateOf(PrayerInputState())

    var isSeeding by mutableStateOf(true)
        private set

    @OptIn(ExperimentalTime::class)
    fun refresh(
        latitude: Double = 37.9601,
        longitude: Double = 58.3261,
        date: LocalDate = LocalDate.now()
    ) {
        val info = getCurrentPrayerInfo(
            PrayerInput(latitude, longitude, date)
        )
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ashgabat"))

        prayerState = prayerState.copy(
            latitude = latitude,
            longitude = longitude,
            date = date,
            fajr = formatter.format(Date(info.fajr.toEpochMilliseconds())),
            sunrice = formatter.format(Date(info.sunrise.toEpochMilliseconds())),
            dhuhr = formatter.format(Date(info.dhuhr.toEpochMilliseconds())),
            asr = formatter.format(Date(info.asr.toEpochMilliseconds())),
            magrib = formatter.format(Date(info.maghrib.toEpochMilliseconds())),
            isha = formatter.format(Date(info.isha.toEpochMilliseconds())),
        )
    }

    init {
        viewModelScope.launch {
            if (!preferences.isLaunchedBefore()){
                prayerRepo.seedIfNeeded()
                preferences.saveFirstLaunch()
            }
            isSeeding = false
            refreshForTm(LocalDate.now())
        }

    }


    fun onNextClick() {
        val dateWas = prayerState.date
        prayerState = prayerState.copy(
            date = dateWas.plusDays(1)
        )
        refresh(date = prayerState.date)
    }

    fun onPreviousClick() {
        val dateWas = prayerState.date
        prayerState = prayerState.copy(
            date = dateWas.minusDays(1)
        )
        refresh(date = prayerState.date)
    }

    @OptIn(ExperimentalTime::class)
    private fun refreshForTm(date: LocalDate? = null) {
        val targetDate = LocalDate.now()
        val info = getCurrentPrayerInfo(
            PrayerInput(37.9601, 58.3261, targetDate)
        )
        val sunriseMillis = info.sunrise.toEpochMilliseconds()
        val sunsetMillis = info.maghrib.toEpochMilliseconds()
        if (date == null) {
            viewModelScope.launch {
                val result = prayerRepo.getForToday()
                result?.let {
                    val hijriD = LocalDate.now().toHijriString()
                    prayerState = prayerState.copy(
                        date = LocalDate.now(),
                        hijriDate = LocalDate.now().toHijri(),
                        fajr = result.fajr,
                        sunrice = result.sunrise,
                        dhuhr = result.dhuhr,
                        asr = result.asr,
                        magrib = result.maghrib,
                        isha = result.isha,
                        sunriseMillis = sunriseMillis,
                        sunsetMillis = sunsetMillis,
                        sunProgress = calculateSunProgress(
                            fajrMillis = info.fajr.toEpochMilliseconds(),
                            sunriseMillis = sunriseMillis,
                            sunsetMillis = sunsetMillis,
                            ishaMillis = info.isha.toEpochMilliseconds()
                        )
                    )
                }
            }
        }else{
            viewModelScope.launch {
                val result = prayerRepo.getForDate(date.dayOfMonth,
                    date.monthValue)
                result?.let {
                    prayerState = prayerState.copy(
                        date = date,
                        hijriDate = date.toHijri(),
                        fajr = result.fajr,
                        sunrice = result.sunrise,
                        dhuhr = result.dhuhr,
                        asr = result.asr,
                        magrib = result.maghrib,
                        isha = result.isha,
                        sunsetMillis = sunsetMillis,
                        sunriseMillis = sunriseMillis,
                        sunProgress = calculateSunProgress(
                            fajrMillis = info.fajr.toEpochMilliseconds(),
                            sunriseMillis = sunriseMillis,
                            sunsetMillis = sunsetMillis,
                            ishaMillis = info.isha.toEpochMilliseconds()
                        ),
                        prayerAngles = listOf(
                            timeToAngle(
                                timeMillis = formatStringToTime(result.fajr,date),
                                sunriseMillis = sunriseMillis,
                                sunsetMillis = sunsetMillis),
                        timeToAngle(
                            timeMillis = formatStringToTime(result.dhuhr,date),
                            sunriseMillis = sunriseMillis,
                            sunsetMillis = sunsetMillis),
                            timeToAngle(
                                timeMillis = formatStringToTime(result.asr,date),
                                sunriseMillis = sunriseMillis,
                                sunsetMillis = sunsetMillis),
                            timeToAngle(
                                timeMillis = formatStringToTime(result.maghrib,date),
                                sunriseMillis = sunriseMillis,
                                sunsetMillis = sunsetMillis),
                            timeToAngle(
                                timeMillis = formatStringToTime(result.isha,date),
                                sunriseMillis = sunriseMillis,
                                sunsetMillis = sunsetMillis),)
                    )
                }
            }
        }
    }
    fun OnNextClickForTm(){
        val dateWas = prayerState.date
        prayerState = prayerState.copy(
            date = dateWas.plusDays(1)
        )
        refreshForTm(date = prayerState.date)
    }

    fun OnPreviousClickForTm(){
        val dateWas = prayerState.date
        prayerState = prayerState.copy(
            date = dateWas.minusDays(1)
        )
        refreshForTm(date = prayerState.date)
    }
    private fun calculateSunProgress(
        fajrMillis: Long,
        sunriseMillis: Long,
        sunsetMillis: Long,
        ishaMillis: Long
    ): Float {
        val now = System.currentTimeMillis()
        return when {
            now < sunriseMillis -> 0f
            now > sunsetMillis -> 1f
            else -> (now - sunriseMillis).toFloat() / (sunsetMillis - sunriseMillis).toFloat()
        }
    }

    fun timeToAngle(timeMillis: Long, sunriseMillis: Long, sunsetMillis: Long): Float {
        val progress = (timeMillis - sunriseMillis).toFloat() / (sunsetMillis - sunriseMillis)
        return progress.coerceIn(0f, 1f) * 180f
    }
    private fun formatStringToTime(value: String,date: LocalDate): Long{
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val sunriseTime = LocalTime.parse(value)

            val sunriseMillis = date.atTime(sunriseTime)
                .atZone(ZoneId.of("Asia/Ashgabat"))
                .toInstant().toEpochMilli()

        return sunriseMillis
    }
}