package com.tick.taku.notificationwatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.notificationwatcher.corecomponent.util.showDialog
import com.tick.taku.notificationwatcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by dataBinding()

    private val navController: NavController by lazy {
        findNavController(R.id.fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()

        checkForPermission()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
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
