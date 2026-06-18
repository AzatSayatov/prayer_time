package com.sayatcode.calendar_presentaion.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sayatcode.calendar_presentaion.model.CalendarDay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDayItem(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ringColor = MaterialTheme.colors.primary
    val trackColor = MaterialTheme.colors.onSurface
    val textColor = when {
        !day.isCurrentMonth -> MaterialTheme.colors.onSurface.copy(alpha = 0.35f)
        else -> MaterialTheme.colors.onSurface
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(2.dp)) {
            val strokeWidth = 3.dp.toPx()
            val diameter = minOf(size.width, size.height) - strokeWidth
            val topLeft = androidx.compose.ui.geometry.Offset(
                x = (size.width - diameter) / 2f,
                y = (size.height - diameter) / 2f
            )
            val arcSize = Size(diameter, diameter)

            // background track
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth)
            )

            // progress ring, clockwise from top
            if (day.status.progress > 0f) {
                drawArc(
                    color = ringColor,
                    startAngle = -90f,
                    sweepAngle = 360f * day.status.progress,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth)
                )
            }

            if (isSelected) {
                drawCircle(
                    color = ringColor.copy(alpha = 0.12f),
                    radius = diameter / 2f,
                    center = androidx.compose.ui.geometry.Offset(
                        x = size.width / 2f,
                        y = size.height / 2f
                    )
                )
            }
        }

        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            textAlign = TextAlign.Center,
            style = if (day.isToday) {
                MaterialTheme.typography.h6.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            } else {
                MaterialTheme.typography.h6
            }
        )
    }
}