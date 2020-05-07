package com.tick.taku.notificationwatcher.domain.db.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.MessageDatabase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = [DatabaseModule::class])
interface DatabaseComponent {

    fun messageDatabase(): MessageDatabase

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DatabaseComponent
    }

    companion object {
        fun factory(): Factory = DaggerDatabaseComponent.factory()
    }
}