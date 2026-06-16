package com.sayatcode.perday_presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sayatcode.core.R

@Composable
fun PrayerTimeTopBanner(
    sunProgress : Float = 0.5f,
    prayerAngles: List<Float> = emptyList()
){
    Box(modifier = Modifier.fillMaxWidth()
        .clip(
            RoundedCornerShape(
                bottomStart = 20.dp,
                bottomEnd = 20.dp))
        .background(color = MaterialTheme.colors.primary)
        .height(300.dp)){


        Image(
            modifier = Modifier.fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp)),
            painter = painterResource(R.drawable.sunrice),
            contentDescription = "sunrice_img",
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                modifier = Modifier.weight(1f)
                    .size(50.dp)
                    .clip(
                        RoundedCornerShape(10.dp)),
                painter = painterResource(R.drawable.sunrice),
                contentDescription = "sunrice_img",
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier.weight(1f)
                    .size(50.dp)
                    .clip(
                        RoundedCornerShape(10.dp)),
                painter = painterResource(R.drawable.sunrice),
                contentDescription = "sunrice_img",
                contentScale = ContentScale.Crop
            )
        }
        SunArc(
            progress = sunProgress,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            prayerAngles = prayerAngles
        )

    }
}