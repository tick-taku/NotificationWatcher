package com.tick.taku.notificationwatcher.view.message.item

import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.ItemMessageBinding
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.xwray.groupie.databinding.BindableItem

// TODO: Fix layout
class MessageItem(private val entity: MessageEntity): BindableItem<ItemMessageBinding>(entity.hashCode().toLong()) {

    companion object {
        private const val DATE_FORMAT = "M/dd\nHH:mm"
    }

    override fun getLayout() = R.layout.item_message

    override fun bind(viewBinding: ItemMessageBinding, position: Int) {
        viewBinding.entity = entity.also {
            viewBinding.date.text = it.dateTime().toString(DATE_FORMAT)
        }

        viewBinding.root.setOnLongClickListener {
            listener?.invoke(entity)
            true
        }
    }

    override fun equals(other: Any?): Boolean = (other as? MessageItem)?.entity == entity
    override fun hashCode(): Int = entity.hashCode()

    private var listener: ((MessageEntity) -> Unit)? = null
    fun setOnLongClickListener(l: (MessageEntity) -> Unit) {
        listener = l
    }

}