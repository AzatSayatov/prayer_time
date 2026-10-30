package com.sayatcode.prayertimes

import java.time.ZoneId

/** A place on Earth. Needed only for [PrayerSource.WORLDWIDE_CALCULATED]. */
public data class Location(
    val latitude: Double,
    val longitude: Double,
    /** Time zone used to convert calculated moments to local times. */
    val timeZone: ZoneId = ZoneId.systemDefault()
) {
    init {
        require(latitude in -90.0..90.0) { "latitude must be in -90..90, was $latitude" }
        require(longitude in -180.0..180.0) { "longitude must be in -180..180, was $longitude" }
    }
}
