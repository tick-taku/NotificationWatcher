package com.tick.taku.notificationwatcher.domain.repository.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.NotificationDatabase
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {

    fun notificationRepository(): NotificationRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, @BindsInstance db: NotificationDatabase): RepositoryComponent
    }

    companion object {
        fun factory(): Factory = DaggerRepositoryComponent.factory()
    }
}