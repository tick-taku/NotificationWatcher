package com.tick.taku.notificationwatcher.domain.db.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.MessageDatabase
import com.tick.taku.notificationwatcher.domain.db.internal.MessageDatabaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule.Provider::class])
internal abstract class DatabaseModule {

    @Binds
    abstract fun bindMessageDatabase(db: MessageDatabaseImpl): MessageDatabase

    @Module
    object Provider {
        @Singleton @Provides
        fun provideMessageDatabase(context: Context): MessageDatabaseImpl {
            return MessageDatabaseImpl.getInstance(context)
        }
    }

}