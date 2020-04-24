package com.tick.taku.notificationwatcher.di

import android.app.Application
import com.tick.taku.notificationwatcher.MainActivityBinder
import com.tick.taku.notificationwatcher.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, MainActivityBinder::class])
interface AppComponent: AndroidInjector<MyApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

}

fun Application.createAppComponent() = DaggerAppComponent.factory().create(this)