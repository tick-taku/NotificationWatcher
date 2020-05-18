package com.tick.taku.notificationwatcher.view.message.item

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import com.tick.taku.notificationwatcher.view.databinding.ItemMessageBinding
import com.tick.taku.notificationwatcher.view.message.viewmodel.MessageViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.databinding.GroupieViewHolder

// TODO: Fix layout
class MessageItem(private val entity: UserMessageEntity,
                  private val viewModel: MessageViewModel,
                  private val lifecycleOwner: LifecycleOwner,
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

        viewModel.isShowUrlPreview.observe(lifecycleOwner) { isShow ->
            viewBinding.previews.run {
                isGone = true
                if (isShow) setupMessage()
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

    private fun RecyclerView.setupMessage() {
        urls.takeIf { it.isNotEmpty() }?.map {
            MessageUrlPreviewItem(it).apply {
                setOnPreviewError { pos ->
                    if (!isComputingLayout && pos in 0 until previewAdapter.groupCount)
                        previewAdapter.removeGroupAtAdapterPosition(pos)
                }
            }
        }?.let { items ->
            isVisible = true
            adapter = previewAdapter.also {
                it.update(items)
            }
        }
    }

    override fun equals(other: Any?): Boolean = (other as? MessageItem)?.entity == entity
    override fun hashCode(): Int = entity.hashCode()

    private var messageClickListener: ((String) -> Unit)? = null
    fun setOnMessageClickListener(l: (String) -> Unit) { messageClickListener = l }

    private var longClickListener: ((MessageEntity) -> Unit)? = null
    fun setOnLongClickListener(l: (MessageEntity) -> Unit) { longClickListener = l }

}