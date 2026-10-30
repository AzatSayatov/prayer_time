package com.sayatcode.prayertimes

/** Options for [PrayerSource.WORLDWIDE_CALCULATED]. Ignored by the Turkmenistan table. */
public data class CalculationOptions(
    val method: CalculationMethod = CalculationMethod.MUSLIM_WORLD_LEAGUE,
    val asrSchool: AsrSchool = AsrSchool.STANDARD
)

public enum class CalculationMethod {
    MUSLIM_WORLD_LEAGUE, EGYPTIAN, KARACHI, UMM_AL_QURA, DUBAI, QATAR, KUWAIT, SINGAPORE, TURKEY, NORTH_AMERICA
}

public enum class AsrSchool {
    /** Shafi'i, Maliki, Hanbali: shadow equals object length. */
    STANDARD,

    /** Hanafi: shadow equals twice the object length (later Asr). */
    HANAFI
}
