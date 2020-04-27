package com.tick.taku.notificationwatcher.domain.db.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.NotificationDatabase
import com.tick.taku.notificationwatcher.domain.db.internal.NotificationDatabaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule.Provider::class])
internal abstract class DatabaseModule {

    @Binds
    abstract fun bindNotificationDatabase(db: NotificationDatabaseImpl): NotificationDatabase

    @Module
    object Provider {
        @Singleton @Provides
        fun provideNotificationDatabase(context: Context): NotificationDatabaseImpl {
            return NotificationDatabaseImpl.getInstance(context)
        }
    }

}