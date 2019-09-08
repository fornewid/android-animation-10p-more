package soup.animation.sample.drawable.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService

object NotificationChannels {

    const val NOTIFICATION = "NOTIFICATION"

    fun init(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.getSystemService<NotificationManager>()
                ?.createNotificationChannels(createChannels())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannels(): List<NotificationChannel> {
        return listOf(
            NotificationChannel(
                NOTIFICATION,
                "Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
        )
    }
}
