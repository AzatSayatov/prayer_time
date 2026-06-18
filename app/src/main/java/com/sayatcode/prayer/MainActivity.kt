package com.sayatcode.prayer

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sayatcode.calendar_presentaion.ui.CalendarScreen
import com.sayatcode.core_ui.ProvideStrings
import com.sayatcode.core_ui.rememberStrings
import com.sayatcode.perday_presentation.domain.PrayerPerDayViewModel
import com.sayatcode.perday_presentation.presentation.PrayerTimeOfDayScreen
import com.sayatcode.prayer.ui.theme.PrayerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val lyricist = rememberStrings()
            PrayerTheme(darkTheme = false) {
                ProvideStrings(lyricist = lyricist) {
                    Scaffold(modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets.safeContent) { innerPadding ->
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalTime::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier,
             viewModel: PrayerPerDayViewModel = hiltViewModel()
) {
    val isSeeding = viewModel.isSeeding
    if (isSeeding){
        Box(Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }else {
        CalendarScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrayerTheme {
        Greeting("Android")
    }
}