package com.tick.taku.notificationwatcher

import com.tick.taku.notificationwatcher.di.AppComponent
import com.tick.taku.notificationwatcher.di.AppInjector
import com.tick.taku.notificationwatcher.di.createAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class MyApplication: DaggerApplication() {

    private val appComponent: AppComponent by lazy {
        createAppComponent()
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    override fun onCreate() {
        super.onCreate()

        AppInjector.initialize(this)

        Timber.plant(Timber.DebugTree())
    }

}