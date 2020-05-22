package com.tick.taku.widget.internal

import android.util.LruCache
import com.tick.taku.android.corecomponent.ktx.guard
import com.tick.taku.widget.internal.mapper.toSourceContent
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

internal object ContentCrawler: CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    private val cache = LruCache<String, SourceContent>(1024 * 1024)

    fun obtainUrlPreviewAsync(webUrl: String) = async {
        cache.get(webUrl).takeIf { it?.title?.isNotEmpty() == true } guard {
            connect(webUrl)?.toSourceContent().also {
                cache.put(webUrl, it)
            }
        }
    }

    private fun connect(webUrl: String): Document? {
        Timber.d("Try to connect $webUrl.")
        return runCatching {
            Jsoup.connect(webUrl).userAgent("Mozilla").get()
        }
            .onFailure { Timber.e("Failed to connect $webUrl. <$it>") }
            .getOrThrow()
    }

}