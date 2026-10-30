# prayertimes

Prayer times with **two explicit sources**. You choose the source; every result tells you which one it came from.

| Method | Source (`PrayerSource`) | Needs location | Works in |
|---|---|---|---|
| `client.turkmenistan(date)` | `TURKMENISTAN_OFFICIAL` | no | Turkmenistan only |
| `client.worldwide(location, date)` | `WORLDWIDE_CALCULATED` | yes | anywhere |

```kotlin
val client = PrayerTimesClient()

val tm = client.turkmenistan(LocalDate.now())
val london = client.worldwide(Location(51.5074, -0.1278, ZoneId.of("Europe/London")), LocalDate.now())

println("${tm.source.title}: fajr ${tm.fajr}")        // Turkmenistan (official table): fajr 07:12
println("${london.source.title}: fajr ${london.fajr}") // Worldwide (calculated): fajr ...
```

## Which one to use?
- User is in Turkmenistan -> `turkmenistan()`: matches the local table.
- Everywhere else -> `worldwide()`. Choose `CalculationOptions(method, asrSchool)` to match your region.
- Show `result.source.title` in your UI so users know what kind of times they see.

## Turkmenistan table details
The table stores only fajr and asr. Others are derived: sunrise = fajr + 70 min, dhuhr = 13:30,
maghrib = asr + 100 min, isha = asr + 180 min. Times are in `Asia/Ashgabat`. Same table every year.

## Install
Local test: `./gradlew :prayertimes:publishToMavenLocal`, then
`implementation("com.sayatcode:prayer-times:0.1.0")` with `mavenLocal()`.

Requires Java 21 class files (Adhan2 dependency). Android: use core library desugaring for `java.time` below API 26.
