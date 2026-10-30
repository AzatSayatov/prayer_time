package com.sayatcode.prayertimes

/**
 * Where the prayer times come from. Every [PrayerTimes] result carries the source it was
 * produced from, so an app can always show the user which kind of times they are looking at.
 */
public enum class PrayerSource(public val title: String, public val description: String) {

    /**
     * Fixed table bundled inside the library (one row per calendar day, same every year).
     * Valid for Turkmenistan only. No location is needed.
     */
    TURKMENISTAN_OFFICIAL(
        title = "Turkmenistan (official table)",
        description = "Times from the bundled Turkmenistan table. Works only for Turkmenistan."
    ),

    /**
     * Astronomical calculation from latitude/longitude using the Adhan algorithm.
     * Works for any place on Earth, but may differ by several minutes from local mosque tables.
     */
    WORLDWIDE_CALCULATED(
        title = "Worldwide (calculated)",
        description = "Times calculated from coordinates. Works anywhere in the world."
    )
}
