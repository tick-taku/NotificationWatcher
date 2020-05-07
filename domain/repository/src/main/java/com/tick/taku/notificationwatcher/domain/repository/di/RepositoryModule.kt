package com.tick.taku.notificationwatcher.domain.repository.di

import com.tick.taku.notificationwatcher.domain.repository.MessageRepository
import com.tick.taku.notificationwatcher.domain.repository.internal.MessageRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class RepositoryModule {

    @Binds
    abstract fun provideMessageRepository(impl: MessageRepositoryImpl): MessageRepository

}