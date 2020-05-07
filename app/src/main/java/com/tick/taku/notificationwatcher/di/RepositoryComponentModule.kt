package com.tick.taku.notificationwatcher.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.MessageDatabase
import com.tick.taku.notificationwatcher.domain.repository.MessageRepository
import com.tick.taku.notificationwatcher.domain.repository.di.RepositoryComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryComponentModule {

    @Singleton @Provides
    fun provideMessageRepository(component: RepositoryComponent): MessageRepository {
        return component.messageRepository()
    }

    @Singleton @Provides
    fun providesRepositoryComponent(context: Context, db: MessageDatabase): RepositoryComponent {
        return RepositoryComponent.factory().create(context, db)
    }

}