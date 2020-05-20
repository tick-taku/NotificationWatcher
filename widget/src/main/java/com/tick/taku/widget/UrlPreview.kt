package com.tick.taku.widget

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
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
import com.tick.taku.widget.databinding.LayoutUrlPreviewBinding
import com.tick.taku.widget.internal.ContentCrawler
import com.tick.taku.widget.internal.SourceContent
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import kotlin.coroutines.CoroutineContext

class UrlPreview: FrameLayout, CoroutineScope {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onRenderedError?.invoke(throwable)
    }
    private val coroutineScope = CoroutineScope(coroutineContext + exceptionHandler)

    var url: String = ""
        set(value) {
            field = value.takeIf { it.toUri().isWebUrlSchema() } guard {
                onRenderedError?.invoke(IllegalArgumentException("Argument is not web url."))
                return
            }
            makePreview(field)
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

    private val binding: LayoutUrlPreviewBinding by lazy {
        DataBindingUtil.inflate<LayoutUrlPreviewBinding>(LayoutInflater.from(context), R.layout.layout_url_preview, this, true)
    }
    private var connection: Deferred<SourceContent?>? = null

    private val previewImageRadius: Float by lazy {
        context.resources.getDimension(R.dimen.url_preview_image_radius)
    }

    private fun makePreview(webUrl: String) {
        coroutineScope.launch {
            ContentCrawler.obtainUrlPreviewAsync(webUrl).also { connection = it }.await()?.let {
                renderPreview(it)
                connection = null
            }
        }
    }

    @MainThread
    private fun renderPreview(entity: SourceContent) {
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (title.isEmpty())
            makePreview(url)
    }

    override fun onDetachedFromWindow() {
        connection?.cancel()
        onRenderedSuccess = null
        onRenderedError = null

        super.onDetachedFromWindow()
    }

    private var onRenderedSuccess: (() -> Unit)? = null
    private var onRenderedError: ((Throwable) -> Unit)? = null
    fun setOnRenderedListener(onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        onRenderedSuccess = onSuccess
        onRenderedError = onError
    }
}