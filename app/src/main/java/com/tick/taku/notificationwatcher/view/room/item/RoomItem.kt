package com.tick.taku.notificationwatcher.view.room.item

import coil.api.load
import coil.transform.CircleCropTransformation
import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.ItemRoomBinding
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import com.xwray.groupie.databinding.BindableItem

// TODO: Fix layout
data class RoomItem(private val entity: RoomInfoEntity): BindableItem<ItemRoomBinding>(entity.hashCode().toLong()) {

    companion object {
        private const val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss"
    }

    override fun getLayout() = R.layout.item_room

    override fun bind(viewBinding: ItemRoomBinding, position: Int) {
        viewBinding.entity = entity

        viewBinding.date.text =
            entity.latestMessage.localTime().toString(DATE_FORMAT)

        viewBinding.icon.load(R.mipmap.ic_launcher) {
            transformations(CircleCropTransformation())
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