package soup.animation.sample.drawable.notification

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_drawable_notification.*
import soup.animation.sample.R

class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drawable_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNotification.setOnClickListener {
            it.context.showNotification()
        }
    }

    private fun Context.showNotification() {
        val notification = NotificationCompat
            .Builder(this,
                NotificationChannels.NOTIFICATION
            )
            .setContentTitle("AnimationDrawable")
            .setContentText("Animated icon will be shown!")
            .setSmallIcon(R.drawable.stat_sys_download)
            .build()
        getSystemService<NotificationManager>()?.notify(1, notification)
    }
}
