package com.tick.taku.notificationwatcher.view.message.item

import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.view.databinding.ItemMessageHeaderBinding
import com.xwray.groupie.databinding.BindableItem

class MessageHeaderItem(private val date: String): BindableItem<ItemMessageHeaderBinding>(date.hashCode().toLong()) {

    override fun getLayout() = R.layout.item_message_header

    override fun bind(viewBinding: ItemMessageHeaderBinding, position: Int) {
        viewBinding.label.text = date
    }

}