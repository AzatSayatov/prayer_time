package com.sayatcode.core.use_case

import android.os.Build
import androidx.annotation.RequiresApi
import com.batoulapps.adhan2.CalculationMethod
import com.batoulapps.adhan2.Coordinates
import com.batoulapps.adhan2.Madhab
import com.batoulapps.adhan2.PrayerAdjustments
import com.batoulapps.adhan2.PrayerTimes
import com.batoulapps.adhan2.data.DateComponents
import com.sayatcode.core.model.PrayerInput
import jakarta.inject.Inject

class GetPrayerInfoForDate @Inject constructor(){
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(input: PrayerInput) : PrayerTimes {
        val coordinates = Coordinates(input.latitude, input.longitude)
        val date = DateComponents(
            input.date.year,
            input.date.monthValue,
            input.date.dayOfMonth
        )
        val params = CalculationMethod.KARACHI.parameters
            .copy(madhab = Madhab.HANAFI,
                prayerAdjustments =
                    PrayerAdjustments(
                        fajr = 44,
                        sunrise = -1,
                        asr = 46,
                        maghrib = 12,
                        isha = -23
                    )
            )
        val prayerTimes = PrayerTimes(
            coordinates,
            date,
            calculationParameters = params
        )
        return  prayerTimes
    }
}