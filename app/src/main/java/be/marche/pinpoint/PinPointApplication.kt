package be.marche.pinpoint

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import be.marche.pinpoint.geolocation.LocationService

class PinPointApplication : Application() {

    override fun onCreate() {
        super.onCreate()

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