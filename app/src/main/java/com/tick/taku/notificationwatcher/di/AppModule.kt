package com.tick.taku.notificationwatcher.di

import android.app.Application
import com.tick.taku.notificationwatcher.domain.db.NotificationDataBase
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import com.tick.taku.notificationwatcher.domain.repository.internal.NotificationRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton @Provides
    fun provide(app: Application): NotificationRepository {
        val db = NotificationDataBase.getInstance(app)
        return NotificationRepositoryImpl(db)
    }

}