package com.tick.taku.notificationwatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import com.tick.taku.notificationwatcher.corecomponent.util.showDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkForPermission()
    }

    /**
     * Check permission for notification listener service is granted.
     */
    private fun checkForPermission() {
        if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(packageName))
            showDialog {
                setMessage(R.string.notification_listener_denied)
                setNegativeButton(R.string.cancel)
                setPositiveButton(R.string.ok) {
                    startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                }
            }
    }
}
