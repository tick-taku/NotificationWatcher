package com.tick.taku.notificationwatcher.domain.db.entity.internal

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.tick.taku.android.corecomponent.extensions.pixelEquals
import com.tick.taku.notificationwatcher.domain.db.entity.LatestMessage
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity

data class RoomInfoEntityImpl(@ColumnInfo(name = RoomEntityImpl.PRIMARY_KEY) override val id: String,
                              override val name: String,
                              @Embedded(prefix = "latest_") override val latestMessage: LatestMessageImpl): RoomInfoEntity

data class LatestMessageImpl(override val message: String,
                             override val date: Long,
                             override val icon: Bitmap): LatestMessage {
    override fun equals(other: Any?): Boolean =
        (other as? LatestMessage)?.let { it.message == message && it.date == date && it.icon.pixelEquals(icon) } ?: false
    override fun hashCode(): Int = (message + date).hashCode()
}