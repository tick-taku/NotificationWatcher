package com.tick.taku.notificationwatcher.di

import android.app.Application
import com.tick.taku.notificationwatcher.MainActivityModule
import com.tick.taku.notificationwatcher.MyApplication
import com.tick.taku.notificationwatcher.notification.NotificationWatcherModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    MainActivityModule::class,
    NotificationWatcherModule::class,
    RepositoryComponentModule::class,
    DatabaseComponentModule::class
])
interface AppComponent: AndroidInjector<MyApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

}

fun Application.createAppComponent() = DaggerAppComponent.factory().create(this)