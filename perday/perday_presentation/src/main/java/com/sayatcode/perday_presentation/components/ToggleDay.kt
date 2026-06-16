package com.sayatcode.perday_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ToggleDay(
    modifier: Modifier = Modifier,
    onPreviousClick:() -> Unit,
    onNextClick:() -> Unit,
    miladyDate: String,
    hijriDate: String
){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 25.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(
            color = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(20.dp)
        )
        .padding(2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = onPreviousClick
        ) {
            Icon(
                imageVector = Icons.Default.NavigateBefore,
                contentDescription = null
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = miladyDate,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = hijriDate
            )
        }

        IconButton(
            onClick = onNextClick
        ) {
            Icon(
                imageVector = Icons.Default.NavigateNext,
                contentDescription = null,
            )
        }
    }
}