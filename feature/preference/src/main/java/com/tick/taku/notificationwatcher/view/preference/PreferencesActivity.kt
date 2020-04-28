package com.tick.taku.notificationwatcher.view.preference

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.openActivity
import com.tick.taku.notificationwatcher.view.preference.databinding.ActivityPreferencesBinding

class PreferencesActivity: AppCompatActivity(R.layout.activity_preferences) {

    private val binding: ActivityPreferencesBinding by dataBinding()

    private val navController: NavController by lazy {
        findNavController(R.id.prefs_fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, AppBarConfiguration(setOf()))
    }

    override fun onSupportNavigateUp() = run {
        onBackPressedDispatcher.onBackPressed()
        false
    }

    companion object {
        fun start(context: Context) {
            context.openActivity<PreferencesActivity>()
        }
    }

}