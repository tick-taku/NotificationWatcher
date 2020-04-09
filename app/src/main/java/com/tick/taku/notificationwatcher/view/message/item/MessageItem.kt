package com.tick.taku.notificationwatcher.view.message.item

import androidx.core.view.isVisible
import coil.api.load
import coil.transform.CircleCropTransformation
import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.ItemMessageBinding
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import com.xwray.groupie.databinding.BindableItem

// TODO: Fix layout
class MessageItem(private val entity: UserMessageEntity,
                  private val isShowName: Boolean = true): BindableItem<ItemMessageBinding>(entity.hashCode().toLong()) {

    companion object {
        private const val DATE_FORMAT = "M/dd\nHH:mm"
    }

    override fun getLayout() = R.layout.item_message

    override fun bind(viewBinding: ItemMessageBinding, position: Int) {
        viewBinding.entity = entity.also {
            viewBinding.date.text = it.message.localTime().toString(DATE_FORMAT)
        }

        viewBinding.user.isVisible = isShowName

        viewBinding.icon.load(entity.user.icon) {
            // TODO : Icon disappear when screen rotated.
//            transformations(CircleCropTransformation())
        }

        viewBinding.message.setOnClickListener {
            messageClickListener?.invoke(entity.message.message)
        }

        viewBinding.root.setOnLongClickListener {
            longClickListener?.invoke(entity.message)
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