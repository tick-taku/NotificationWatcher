package com.tick.taku.android.corecomponent.di

import android.content.SharedPreferences

// TODO: For get injection on attachBaseContext.
interface DirectlyModuleProvider {
    fun providerSharedPrefs(): SharedPreferences
}