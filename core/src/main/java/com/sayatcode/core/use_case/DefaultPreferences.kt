package com.sayatcode.core.use_case

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sayatcode.core.preferenes.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
) : Preferences {
    override fun saveFirstLaunch() {
        sharedPreferences.edit {
            putBoolean(Preferences.KEY_FIRST_LAUNCH, true)
        }
    }

    override fun isLaunchedBefore(): Boolean {
        return sharedPreferences.getBoolean(
            Preferences.KEY_FIRST_LAUNCH, false
        )
    }
}