package com.tick.taku.notificationwatcher.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz

data class RoomInfoEntity(@ColumnInfo(name = RoomEntity.PRIMARY_KEY) val id: String,
                          val name: String,
                          @Embedded(prefix = "latest_") val latestMessage: LatestMessage)

data class LatestMessage(val message: String,
                         val date: Long) {
    fun localTime(): DateTimeTz = DateTime(date).local
}