package com.tick.taku.notificationwatcher.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.api.account.AccountClient
import com.tick.taku.notificationwatcher.domain.api.di.ApiComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApiComponentModule {

    @Singleton @Provides
    fun provideAccountClient(context: Context): AccountClient {
        return ApiComponent.factory().create(context).accountClient()
    }

}