package com.tick.taku.notificationwatcher.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.NotificationDatabase
import com.tick.taku.notificationwatcher.domain.db.di.DatabaseComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseComponentModule {

    @Singleton @Provides
    fun provideNotificationDatabase(context: Context): NotificationDatabase {
        return DatabaseComponent.factory().create(context).notificationDatabase()
    }

}