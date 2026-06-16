package com.sayatcode.core_ui.use_case.locale

import androidx.annotation.Keep
import cafe.adriel.lyricist.LyricistStrings

@Keep
data class Strings(
    val january: String,
    val hijriString:(day:Int,month:Int,year:Int) -> String,
)