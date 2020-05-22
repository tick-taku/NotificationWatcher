package com.tick.taku.android.corecomponent.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.request.CachePolicy
import timber.log.Timber

@BindingAdapter("app:load", "app:cachePolicy", requireAll = false)
fun ImageView.loadImage(url: String?, diskCachePolicy: CachePolicy = CachePolicy.DISABLED) {
    Timber.d("Required image url: <$url>")
    url?.takeIf { it.isNotEmpty() }?.let {
        load(it) {
            diskCachePolicy(diskCachePolicy)
        }
    }
}
