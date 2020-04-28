package com.tick.taku.notificationwatcher.di

import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Singleton

@Module
object TimberModule {

    @Singleton @Provides
    fun provideTimber(): Timber.Tree {
        return Timber.DebugTree()
    }

}