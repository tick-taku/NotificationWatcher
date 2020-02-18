package com.tick.taku.notificationwatcher.view.message.item

import coil.api.load
import coil.transform.CircleCropTransformation
import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.ItemMessageBinding
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.xwray.groupie.databinding.BindableItem

// TODO: Fix layout
class MessageItem(private val entity: MessageEntity): BindableItem<ItemMessageBinding>(entity.hashCode().toLong()) {

    companion object {
        private const val DATE_FORMAT = "M/dd HH:mm"
    }

    override fun getLayout() = R.layout.item_message

    override fun bind(viewBinding: ItemMessageBinding, position: Int) {
        viewBinding.entity = entity.also {
            viewBinding.date.text = it.localTime().toString(DATE_FORMAT)
        }

        viewBinding.icon.load(R.drawable.ic_tmp) {
            transformations(CircleCropTransformation())
        }

        viewBinding.message.setOnClickListener {
            messageClickListener?.invoke(entity.message)
        }

        viewBinding.root.setOnLongClickListener {
            longClickListener?.invoke(entity)
            true
        }
    }

    override fun equals(other: Any?): Boolean = (other as? MessageItem)?.entity == entity
    override fun hashCode(): Int = entity.hashCode()

    private var messageClickListener: ((String) -> Unit)? = null
    fun setOnMessageClickListener(l: (String) -> Unit) { messageClickListener = l }

    private var longClickListener: ((MessageEntity) -> Unit)? = null
    fun setOnLongClickListener(l: (MessageEntity) -> Unit) { longClickListener = l }

}