package com.tick.taku.notificationwatcher.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module(includes = [CoilModule::class, TimberModule::class])
abstract class AppModule {

    @Binds abstract fun provide(app: Application): Context

}