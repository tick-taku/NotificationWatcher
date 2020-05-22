package com.tick.taku.notificationwatcher.di

import android.content.Context
import android.content.SharedPreferences
import com.tick.taku.notificationwatcher.domain.api.account.AccountClient
import com.tick.taku.notificationwatcher.domain.db.MessageDatabase
import com.tick.taku.notificationwatcher.domain.repository.AccountRepository
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
    fun provideAccountRepository(component: RepositoryComponent): AccountRepository {
        return component.accountRepository()
    }

    @Singleton @Provides
    fun providesRepositoryComponent(context: Context,
                                    db: MessageDatabase,
                                    client: AccountClient,
                                    prefs: SharedPreferences): RepositoryComponent {
        return RepositoryComponent.factory().create(context, db, client, prefs)
    }

}