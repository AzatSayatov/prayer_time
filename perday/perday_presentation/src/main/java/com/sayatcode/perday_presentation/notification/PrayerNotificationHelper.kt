package com.sayatcode.perday_presentation.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sayatcode.perday_presentation.R

/**
 * Отвечает за создание канала уведомлений и показ уведомления о намазе.
 *
 * Звук: ищет res/raw/azan по имени ресурса во время выполнения через
 * resources.getIdentifier — если файла нет в проекте, тихо откатывается
 * на системный звук уведомлений по умолчанию, не крашась.
 *
 * Когда позже положишь файл азана, назови его res/raw/azan.mp3 (или .ogg/.wav) —
 * больше никаких изменений в коде не потребуется.
 */
object PrayerNotificationHelper {

    private const val CHANNEL_ID = "prayer_times_channel"
    private const val RAW_AZAN_RESOURCE_NAME = "azan"

    private fun resolveSoundUri(context: Context): Uri {
        val resId = context.resources.getIdentifier(
            RAW_AZAN_RESOURCE_NAME,
            "raw",
            context.packageName
        )
        return if (resId != 0) {
            Uri.parse("android.resource://${context.packageName}/$resId")
        } else {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
    }

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val manager = context.getSystemService(NotificationManager::class.java)
        val existing = manager.getNotificationChannel(CHANNEL_ID)
        if (existing != null) return // канал с этим ID нельзя пересоздать с новым звуком — создаём один раз

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Wagt namaz",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Namaz wagty üçin bildirişler"
            setSound(resolveSoundUri(context), audioAttributes)
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showPrayerNotification(context: Context, type: PrayerAlarmType) {
        createChannel(context)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(com.sayatcode.core.R.drawable.sunrice)
            .setContentTitle(type.displayName)
            .setContentText("Namaz wagty geldi")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(type.requestCode, notification)
    }
}