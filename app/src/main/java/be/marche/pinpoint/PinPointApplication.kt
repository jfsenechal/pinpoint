package be.marche.pinpoint

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import be.marche.pinpoint.di.appModule
import be.marche.pinpoint.geolocation.LocationService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PinPointApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PinPointApplication)
            modules(appModule)
        }

        val channel = NotificationChannel(
            LocationService.LOCATION_CHANNEL,
            "Location",
            NotificationManager.IMPORTANCE_LOW
        )

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}