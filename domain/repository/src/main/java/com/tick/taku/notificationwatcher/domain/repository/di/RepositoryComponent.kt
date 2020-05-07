package com.tick.taku.notificationwatcher.domain.repository.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.MessageDatabase
import com.tick.taku.notificationwatcher.domain.repository.MessageRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {

    fun messageRepository(): MessageRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, @BindsInstance db: MessageDatabase): RepositoryComponent
    }

    companion object {
        fun factory(): Factory = DaggerRepositoryComponent.factory()
    }
}