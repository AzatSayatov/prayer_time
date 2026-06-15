package com.sayatcode.core.preferenes

interface Preferences {
    fun saveFirstLaunch()
    fun isLaunchedBefore(): Boolean

    companion object{
        const val KEY_FIRST_LAUNCH = "first_launch"
    }
}