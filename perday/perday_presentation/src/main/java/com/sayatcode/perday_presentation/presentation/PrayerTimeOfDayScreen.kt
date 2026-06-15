package com.sayatcode.perday_presentation.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sayatcode.core.R
import com.sayatcode.core_ui.LocalStrings
import com.sayatcode.perday_presentation.components.PrayerTimeTopBanner
import com.sayatcode.perday_presentation.domain.PrayerPerDayViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrayerTimeOfDayScreen(
    viewModel: PrayerPerDayViewModel = hiltViewModel()
){
    val strings = LocalStrings.current
    val state = viewModel.prayerState


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PrayerTimeTopBanner(sunProgress = state.sunProgress)

        Spacer(Modifier.height(20.dp))
        Text(
            text = "${state.date.dayOfMonth}.${state.date.monthValue}.${state.date.year}"
        )
        Spacer(Modifier.height(20.dp))
        val (day, month, year) = state.hijriDate
        Text(
            text = strings.hijriString(day,month,year)
        )
        Spacer(Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
        ) {
            IconButton(
                onClick = {
                    viewModel.OnPreviousClickForTm()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.NavigateBefore,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    viewModel.OnNextClickForTm()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.NavigateNext,
                    contentDescription = null,
                    tint = Color.White
                )
            }

        }
        Text(
            text = "Ertir ${state.fajr}",
        )
        Text(
            text = "Gun ${state.sunrice}",
        )
        Text(
            text = "Oyle ${state.dhuhr}",
        )
        Text(
            text = "Ikindi ${state.asr}",
        )
        Text(
            text = "agsam ${state.magrib}",
        )
        Text(
            text = "yassy ${state.isha}",
        )
    }
}