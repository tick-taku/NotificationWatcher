package com.tick.taku.notificationwatcher.view.widget

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.LruCache
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.MainThread
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import coil.api.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.tick.taku.android.corecomponent.ktx.guard
import com.tick.taku.android.corecomponent.ktx.isWebUrlSchema
import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.view.databinding.ViewUrlPreviewBinding
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import timber.log.Timber
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.util.*
import kotlin.coroutines.CoroutineContext

private val previewCache = LruCache<String, UrlPreviewEntity>(1024 * 1024)

internal data class UrlPreviewEntity(val title: String,
                                     val description: String,
                                     val imageUrl: String)

class UrlPreview: FrameLayout, CoroutineScope {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    var url: String = ""
        set(value) {
            field = value.takeIf { it.toUri().isWebUrlSchema() } guard {
                onRenderedError?.invoke(IllegalArgumentException("Argument is not web url."))
                return
            }
            showPreview(field)
        }

    var title: String = ""
        @MainThread private set(value) {
            field = value
            binding.title.text = field
        }
    var description: String = ""
        @MainThread private set(value) {
            field = value
            binding.description.text = field
        }
    var imageUrl: String = ""
        @MainThread private set(value) {
            field = value
            binding.image.load(field) {
                diskCachePolicy(CachePolicy.ENABLED)
                transformations(RoundedCornersTransformation(previewImageRadius))
            }
        }

    private val binding: ViewUrlPreviewBinding by lazy {
        DataBindingUtil.inflate<ViewUrlPreviewBinding>(LayoutInflater.from(context), R.layout.view_url_preview, this, true)
    }
    private var connection: Deferred<Document?>? = null

    private val previewImageRadius: Float by lazy {
        context.resources.getDimension(R.dimen.url_preview_image_radius)
    }

    private fun showPreview(webUrl: String) {
        previewCache.get(webUrl).takeIf { it?.title?.isNotEmpty() == true }?.let { renderPreview(it) }
            ?: obtainUrlPreview(webUrl)
    }

    private fun obtainUrlPreview(webUrl: String) {
        launch {
            connectAsync(webUrl).also { connection = it }.await()?.let {
                val entity = UrlPreviewEntity(it.metaTitle, it.description, it.imageUrl)
                withContext(Dispatchers.Main) {
                    renderPreview(entity)
                }
                previewCache.put(webUrl, entity)
            }
            connection = null
        }
    }

    private fun connectAsync(webUrl: String) = async(Dispatchers.IO) {
        Timber.d("Try to connect $webUrl.")
        runCatching {
            Jsoup.connect(webUrl).userAgent("Mozilla").get()
        }
            .onFailure {
                Timber.e("Failed to connect $webUrl. <$it>")
                withContext(Dispatchers.Main) {
                    onRenderedError?.invoke(it)
                }
            }
            .getOrNull()
    }

    private val Document.metaTitle: String
        get() = select("meta[property=og:title]").attr("content").takeIf { it.isNotEmpty() } ?: title()

    private val Document.description: String
        get() {
            val d = select("meta").find {
                it.attr("property") == "og:description" || it.attr("name").toLowerCase(Locale.ROOT) == "description"
            }?.attr("content") ?: ""
            return d.trim().replace("\n", " ")
        }

    private val Document.imageUrl: String
        get() =
            select("meta[property=og:image]").attr("content").takeIf { it.isNotEmpty() } guard {
                select("link").find {
                    when (it.attr("rel").toLowerCase(Locale.ROOT)) {
                        "image_src", "apple-touch-icon", "icon" -> true
                        else -> false
                    }
                }?.attr("href") ?: ""
            }

    @MainThread
    private fun renderPreview(entity: UrlPreviewEntity) {
        if (entity.title.isNotEmpty()) {
            title = entity.title
            description = entity.description
            imageUrl = entity.imageUrl
            onRenderedSuccess?.invoke()
        } else onRenderedError?.invoke(NullPointerException("Obtained title is empty."))
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding.root.setOnClickListener {
            url.toUri().takeIf { it.isWebUrlSchema() }?.let {
                val intent = Intent(Intent.ACTION_VIEW, it)
                context.startActivity(intent)
            }
        }
    }

    private var onRenderedSuccess: (() -> Unit)? = null
    private var onRenderedError: ((Throwable) -> Unit)? = null
    fun setOnRenderedListener(onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        onRenderedSuccess = onSuccess
        onRenderedError = onError
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (title.isEmpty())
            showPreview(url)
    }

    override fun onDetachedFromWindow() {
        connection?.cancel()
        onRenderedSuccess = null
        onRenderedError = null

        super.onDetachedFromWindow()
    }
}