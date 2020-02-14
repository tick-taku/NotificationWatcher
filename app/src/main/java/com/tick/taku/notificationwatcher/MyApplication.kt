package com.tick.taku.notificationwatcher

import android.app.Application
import com.tick.taku.notificationwatcher.domain.db.NotificationDataBase
import timber.log.Timber

class MyApplication: Application() {

    // TODO: DI
    val db: NotificationDataBase by lazy {
        NotificationDataBase.getInstance(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

}