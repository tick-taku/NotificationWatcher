package com.tick.taku.notificationwatcher.view.message.item

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.api.load
import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import com.tick.taku.notificationwatcher.view.databinding.ItemMessageBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.databinding.GroupieViewHolder

// TODO: Fix layout
class MessageItem(private val entity: UserMessageEntity,
                  private val isShowName: Boolean = true): BindableItem<ItemMessageBinding>(entity.hashCode().toLong()) {

    companion object {
        private const val DATE_FORMAT = "HH:mm"
    }

    override fun getLayout() = R.layout.item_message

    private val previewAdapter: GroupAdapter<GroupieViewHolder<*>> by lazy {
        GroupAdapter<GroupieViewHolder<*>>()
    }

    private val urls: List<String> = entity.message.extractWebLink()

    override fun bind(viewBinding: ItemMessageBinding, position: Int) {
        viewBinding.entity = entity.also {
            viewBinding.date.text = it.message.localTime().toString(DATE_FORMAT)
        }

        viewBinding.previews.run {
            isGone = true
            urls.takeIf { it.isNotEmpty() }?.map {
                MessageUrlPreviewItem(it).apply {
                    setOnPreviewError { pos ->
                        if (!isComputingLayout && pos in 0 until previewAdapter.groupCount)
                            previewAdapter.removeGroupAtAdapterPosition(pos)
                    }
                }
            }?.let {
                isVisible = true
                adapter = previewAdapter
                previewAdapter.update(it)
            }
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