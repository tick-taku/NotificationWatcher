package com.tick.taku.notificationwatcher.domain.repository.di

import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import com.tick.taku.notificationwatcher.domain.repository.internal.NotificationRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class RepositoryModule {

    @Binds
    abstract fun provideNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository

}