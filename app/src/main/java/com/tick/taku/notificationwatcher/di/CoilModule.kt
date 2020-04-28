package com.tick.taku.notificationwatcher.di

import android.content.Context
import coil.ImageLoader
import com.tick.taku.notificationwatcher.view.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object CoilModule {

    @Singleton @Provides
    fun provideImageLoader(context: Context): ImageLoader {
        return ImageLoader(context) {
            placeholder(R.drawable.ic_tmp)
            error(R.drawable.ic_tmp)
        }
    }

}