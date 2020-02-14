package com.tick.taku.notificationwatcher.view.room.item

import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.ItemRoomBinding
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import com.xwray.groupie.databinding.BindableItem

// TODO: Fix layout
data class RoomItem(private val entity: RoomInfoEntity): BindableItem<ItemRoomBinding>() {

    companion object {
        private const val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss"
    }

    override fun getLayout() = R.layout.item_room

    override fun bind(viewBinding: ItemRoomBinding, position: Int) {
        viewBinding.entity = entity

        viewBinding.date.text =
            entity.latestMessage.dateTime().toString(DATE_FORMAT)

        viewBinding.root.setOnLongClickListener {
            longClickListener?.invoke(entity)
            true
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is RoomItem) other.entity == entity else false
    }

    override fun hashCode(): Int = entity.hashCode()

    private var longClickListener: ((RoomInfoEntity) -> Unit)? = null
    fun setOnLongClickListener(l: (RoomInfoEntity) -> Unit) {
        longClickListener = l
    }

}