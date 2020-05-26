package com.tick.taku.notificationwatcher.di

import android.content.Context
import coil.ImageLoader
import com.tick.taku.notificationwatcher.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object CoilModule {

    @Singleton @Provides
    fun provideImageLoader(context: Context): ImageLoader {
        return ImageLoader(context) {
            error(R.drawable.ic_error)
        }
    }

}