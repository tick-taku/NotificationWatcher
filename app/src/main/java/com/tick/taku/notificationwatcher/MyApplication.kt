package com.tick.taku.notificationwatcher

import coil.Coil
import coil.ImageLoader
import com.tick.taku.notificationwatcher.di.AppComponent
import com.tick.taku.notificationwatcher.di.AppInjector
import com.tick.taku.notificationwatcher.di.createAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class MyApplication: DaggerApplication() {

    private val appComponent: AppComponent by lazy {
        createAppComponent()
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var logTree: Timber.Tree

    override fun onCreate() {
        super.onCreate()

        AppInjector.initialize(this)

        initModules()
    }

    private fun initModules() {
        Coil.setDefaultImageLoader(imageLoader)
        Timber.plant(logTree)
    }

}