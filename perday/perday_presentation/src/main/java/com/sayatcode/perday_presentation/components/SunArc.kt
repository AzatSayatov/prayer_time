package com.sayatcode.perday_presentation.components

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SunArc(
    progress: Float,
    prayerAngles: List<Float> = emptyList(), // углы намазов в градусах (0..180)
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1500, easing = EaseOutCubic),
        label = "sun_progress"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 24.dp)
    ) {
        val strokeWidth = 2.dp.toPx()
        val arcRect = Rect(
            offset = Offset(0f, 0f),
            size = Size(size.width, size.height * 2)
        )
        val cx = arcRect.center.x
        val cy = arcRect.center.y
        val rx = arcRect.width / 2
        val ry = arcRect.height / 2

        // Дуга
        drawArc(
            color = Color.White.copy(alpha = 0.4f),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = arcRect.topLeft,
            size = arcRect.size,
            style = Stroke(width = strokeWidth)
        )

        // Линии от точки на дуге до нижнего центра
        val bottomCenter = Offset(cx, cy) // центр эллипса = нижний центр дуги

        // Обычные засечки каждые 15 градусов
        for (deg in 0..180 step 15) {
            val rad = Math.toRadians((180.0 + deg))
            val pointOnArc = Offset(
                x = cx + (rx * cos(rad)).toFloat(),
                y = cy + (ry * sin(rad)).toFloat()
            )
            val isPrayer = prayerAngles.any { kotlin.math.abs(it - deg) < 5f }
            drawLine(
                color = if (isPrayer) Color.White else Color.White.copy(alpha = 0.3f),
                start = pointOnArc,
                end = bottomCenter,
                strokeWidth = if (isPrayer) 2.5.dp.toPx() else 1.dp.toPx()
            )
        }

        // Линии намазов (если не попали в step 15)
        prayerAngles.forEach { deg ->
            val rad = Math.toRadians((180.0 + deg))
            val pointOnArc = Offset(
                x = cx + (rx * cos(rad)).toFloat(),
                y = cy + (ry * sin(rad)).toFloat()
            )
            drawLine(
                color = Color.White,
                start = pointOnArc,
                end = bottomCenter,
                strokeWidth = 2.5.dp.toPx()
            )
        }

        // Солнце
        val sunAngle = Math.toRadians((180.0 + animatedProgress * 180.0))
        val sunX = cx + (rx * cos(sunAngle)).toFloat()
        val sunY = cy + (ry * sin(sunAngle)).toFloat()

        drawCircle(
            color = Color(0xFFFFD700).copy(alpha = 0.3f),
            radius = 20.dp.toPx(),
            center = Offset(sunX, sunY)
        )
        drawCircle(
            color = Color(0xFFFFD700),
            radius = 10.dp.toPx(),
            center = Offset(sunX, sunY)
        )
    }
}