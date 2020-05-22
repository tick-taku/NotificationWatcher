package com.tick.taku.notificationwatcher.view.room.item

import coil.api.load
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.view.databinding.ItemRoomBinding
import com.xwray.groupie.databinding.BindableItem

// TODO: Fix layout
data class RoomItem(private val entity: RoomInfoEntity): BindableItem<ItemRoomBinding>(entity.hashCode().toLong()) {

    override fun getLayout() = R.layout.item_room

    override fun bind(viewBinding: ItemRoomBinding, position: Int) {
        viewBinding.entity = entity.also {
            viewBinding.icon.load(it.latestMessage.icon)
        }

        viewBinding.root.run {
            setOnClickListener { listener?.invoke(entity) }
            setOnLongClickListener {
                longClickListener?.invoke(entity)
                true
            }
        }
    }

    override fun equals(other: Any?): Boolean = (other as? RoomItem)?.entity == entity

    override fun hashCode(): Int = entity.hashCode()

    private var listener: ((RoomInfoEntity) -> Unit)? = null
    fun setOnClickListener(l: (RoomInfoEntity) -> Unit) { listener = l }

    private var longClickListener: ((RoomInfoEntity) -> Unit)? = null
    fun setOnLongClickListener(l: (RoomInfoEntity) -> Unit) {
        longClickListener = l
    }

}