package com.tick.taku.notificationwatcher.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.MessageDatabase
import com.tick.taku.notificationwatcher.domain.db.di.DatabaseComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseComponentModule {

    @Singleton @Provides
    fun provideMessageDatabase(context: Context): MessageDatabase {
        return DatabaseComponent.factory().create(context).messageDatabase()
    }

}