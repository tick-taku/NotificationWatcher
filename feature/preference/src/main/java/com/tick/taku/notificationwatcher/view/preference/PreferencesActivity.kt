package com.tick.taku.notificationwatcher.view.preference

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.openActivity
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.android.corecomponent.ui.BaseActivity
import com.tick.taku.android.corecomponent.ui.viewmodel.SystemViewModel
import com.tick.taku.notificationwatcher.view.preference.databinding.ActivityPreferencesBinding
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import javax.inject.Provider

class PreferencesActivity: BaseActivity(R.layout.activity_preferences), HasAndroidInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = injector

    private val binding: ActivityPreferencesBinding by dataBinding()

    @Inject lateinit var systemViewModelFactory: Provider<SystemViewModel>
    private val systemViewModel: SystemViewModel by viewModelProvider {
        systemViewModelFactory.get()
    }

    private val navController: NavController by lazy {
        findNavController(R.id.prefs_fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, AppBarConfiguration(setOf()))

        systemViewModel.locale.observe(this) {
            if (isLocaleChanged(it)) recreate()
        }
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