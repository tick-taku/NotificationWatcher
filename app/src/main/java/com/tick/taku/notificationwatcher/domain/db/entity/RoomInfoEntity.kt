package com.tick.taku.notificationwatcher.domain.db.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz
import com.tick.taku.android.corecomponent.extensions.pixelEquals

data class RoomInfoEntity(@ColumnInfo(name = RoomEntity.PRIMARY_KEY) val id: String,
                          val name: String,
                          @Embedded(prefix = "latest_") val latestMessage: LatestMessage)

data class LatestMessage(val message: String,
                         val date: Long,
                         val icon: Bitmap) {
    fun localTime(): DateTimeTz = DateTime(date).local

    override fun equals(other: Any?): Boolean =
        (other as? LatestMessage)?.let { it.message == message && it.date == date && it.icon.pixelEquals(icon) } ?: false
    override fun hashCode(): Int = (message + date).hashCode()
}