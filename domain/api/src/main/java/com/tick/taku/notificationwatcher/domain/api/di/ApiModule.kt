package com.tick.taku.notificationwatcher.domain.api.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.api.account.AccountClient
import com.tick.taku.notificationwatcher.domain.api.account.internal.AccountApiClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApiModule.Provider::class])
internal abstract class ApiModule {

    @Binds abstract fun provideAccountClient(impl: AccountApiClient): AccountClient

    @Module
    object Provider {
        @Singleton @Provides
        fun provideAccountApiClient(context: Context): AccountApiClient {
            return AccountApiClient(context)
        }
    }

}