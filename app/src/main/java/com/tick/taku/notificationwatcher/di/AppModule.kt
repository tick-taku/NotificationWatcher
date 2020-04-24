package com.tick.taku.notificationwatcher.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton @Provides
    fun provide(app: Application): String {
        return app::class.java.simpleName
    }

}