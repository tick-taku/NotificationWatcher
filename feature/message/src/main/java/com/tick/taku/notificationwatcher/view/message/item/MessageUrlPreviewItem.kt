package com.tick.taku.notificationwatcher.view.message.item

import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.view.databinding.ItemMessageUrlPreviewBinding
import com.xwray.groupie.databinding.BindableItem

data class MessageUrlPreviewItem(private val previewUrl: String)
    : BindableItem<ItemMessageUrlPreviewBinding>(previewUrl.hashCode().toLong()) {

    override fun getLayout(): Int = R.layout.item_message_url_preview

    override fun bind(viewBinding: ItemMessageUrlPreviewBinding, position: Int) {
        viewBinding.preview.run {
            setOnRenderedListener(
                onError = { onPreviewError?.invoke(position) }
            )
            url = previewUrl
        }
    }

    private var onPreviewError: ((Int) -> Unit)? = null
    fun setOnPreviewError(l: (Int) -> Unit) {
        onPreviewError = l
    }
}