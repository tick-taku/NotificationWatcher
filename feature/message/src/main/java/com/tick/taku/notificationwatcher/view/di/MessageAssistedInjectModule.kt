package com.tick.taku.notificationwatcher.view.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_MessageAssistedInjectModule::class])
abstract class MessageAssistedInjectModule