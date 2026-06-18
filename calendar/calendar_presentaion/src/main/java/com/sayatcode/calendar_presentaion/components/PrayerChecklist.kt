package com.sayatcode.calendar_presentaion.components

import com.sayatcode.calendar_domain.model.DayPrayerStatus


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.sayatcode.calendar_domain.model.PrayerName

@Composable
fun PrayerChecklist(
    dayStatus: DayPrayerStatus?,
    onTogglePrayer: (PrayerName) -> Unit,
    modifier: Modifier = Modifier
) {
    val completed = dayStatus?.completed ?: emptySet()

    Column(modifier = modifier.fillMaxWidth()) {
        PrayerName.entries.forEach { prayer ->
            PrayerChecklistRow(
                prayerName = prayer,
                isChecked = prayer in completed,
                onToggle = { onTogglePrayer(prayer) }
            )
        }
    }
}

@Composable
private fun PrayerChecklistRow(
    prayerName: PrayerName,
    isChecked: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = prayerName.displayName(),
            style = MaterialTheme.typography.h6
        )
        RoundCheckbox(isChecked = isChecked, onClick = onToggle)
    }
}

@Composable
private fun RoundCheckbox(
    isChecked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colors.primary
    val backgroundColor = if (isChecked) borderColor else Color.Transparent

    Row(
        modifier = modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .then(
                if (!isChecked) {
                    Modifier.border(
                        width = 2.dp,
                        color = borderColor,
                        shape = CircleShape
                    )
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isChecked) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

private fun PrayerName.displayName(): String = when (this) {
    PrayerName.FAJR -> "Fajr"
    PrayerName.DHUHR -> "Dhuhr"
    PrayerName.ASR -> "Asr"
    PrayerName.MAGHRIB -> "Maghrib"
    PrayerName.ISHA -> "Isha"
}