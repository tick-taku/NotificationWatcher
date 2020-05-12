package com.tick.taku.notificationwatcher.domain.api.di

import android.content.Context
import com.tick.taku.notificationwatcher.domain.api.account.AccountClient
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = [ApiModule::class])
interface ApiComponent {

    fun accountClient(): AccountClient

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApiComponent
    }

    companion object {
        fun factory(): ApiComponent.Factory = DaggerApiComponent.factory()
    }
}