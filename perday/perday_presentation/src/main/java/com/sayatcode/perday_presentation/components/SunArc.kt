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
    progress: Float, // 0f..1f
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1500, easing = EaseOutCubic),
        label = "sun_progress"
    )

    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(horizontal = 24.dp)
    ) {
        val strokeWidth = 2.dp.toPx()
        val arcRect = Rect(
            offset = Offset(0f, 0f),
            size = Size(size.width, size.height * 2)
        )

        // Дуга (пунктир)
        drawArc(
            color = Color.White.copy(alpha = 0.4f),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = arcRect.topLeft,
            size = arcRect.size,
            style = Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f))
            )
        )

        // Позиция солнца на дуге
        val angle = 180f + (animatedProgress * 180f) // 180..360
        val angleRad = Math.toRadians(angle.toDouble())
        val cx = arcRect.center.x
        val cy = arcRect.center.y
        val rx = arcRect.width / 2
        val ry = arcRect.height / 2

        val sunX = cx + (rx * cos(angleRad)).toFloat()
        val sunY = cy + (ry * sin(angleRad)).toFloat()

        // Свечение
        drawCircle(
            color = Color(0xFFFFD700).copy(alpha = 0.3f),
            radius = 20.dp.toPx(),
            center = Offset(sunX, sunY)
        )
        // Солнце
        drawCircle(
            color = Color(0xFFFFD700),
            radius = 10.dp.toPx(),
            center = Offset(sunX, sunY)
        )
    }
}