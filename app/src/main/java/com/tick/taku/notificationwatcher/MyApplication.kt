package com.tick.taku.notificationwatcher

import com.tick.taku.notificationwatcher.di.AppComponent
import com.tick.taku.notificationwatcher.di.AppInjector
import com.tick.taku.notificationwatcher.di.createAppComponent
import com.tick.taku.notificationwatcher.domain.db.NotificationDataBase
import com.tick.taku.notificationwatcher.view.DatabaseTmp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class MyApplication: DaggerApplication() {

    private val appComponent: AppComponent by lazy {
        createAppComponent()
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    // TODO: DI
//    val db: NotificationDataBase by lazy {
//        NotificationDataBase.getInstance(applicationContext)
//    }

    override fun onCreate() {
        super.onCreate()

        AppInjector.initialize(this)

        DatabaseTmp.db = NotificationDataBase.getInstance(applicationContext)

        Timber.plant(Timber.DebugTree())
    }

}