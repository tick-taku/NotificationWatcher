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
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class PreferencesActivity: AppCompatActivity(R.layout.activity_preferences), HasAndroidInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = injector

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

@Module
abstract class PreferencesActivityModule {

    @ContributesAndroidInjector(modules = [PreferencesActivityBinder::class])
    abstract fun contributePreferencesActivity(): PreferencesActivity

    @Module
    abstract class PreferencesActivityBinder {

        @ContributesAndroidInjector
        abstract fun contributePreferencesFragment(): PreferencesFragment

    }

}