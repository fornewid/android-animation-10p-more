package soup.animation.sample

import android.app.Application
import soup.animation.sample.drawable_notification.NotificationChannels
import timber.log.Timber

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        NotificationChannels.init(this)
    }
}
