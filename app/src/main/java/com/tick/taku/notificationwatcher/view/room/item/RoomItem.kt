package com.tick.taku.notificationwatcher.view.room.item

import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.ItemRoomBinding
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity
import com.xwray.groupie.databinding.BindableItem

// TODO: Fix layout
data class RoomItem(private val entity: RoomEntity): BindableItem<ItemRoomBinding>() {

    override fun getLayout() = R.layout.item_room

    override fun bind(viewBinding: ItemRoomBinding, position: Int) {
        viewBinding.entity = entity
    }

    override fun equals(other: Any?): Boolean {
        return if (other is RoomItem) other.entity == entity else false
    }

    override fun hashCode(): Int = entity.hashCode()

}