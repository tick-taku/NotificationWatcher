package com.tick.taku.notificationwatcher.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.NotificationDatabase
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import com.tick.taku.notificationwatcher.domain.repository.di.RepositoryComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryComponentModule {

    @Singleton @Provides
    fun notificationRepository(component: RepositoryComponent): NotificationRepository {
        return component.notificationRepository()
    }

    @Singleton @Provides
    fun providesRepositoryComponent(context: Context, db: NotificationDatabase): RepositoryComponent {
        return RepositoryComponent.factory().create(context, db)
    }

}