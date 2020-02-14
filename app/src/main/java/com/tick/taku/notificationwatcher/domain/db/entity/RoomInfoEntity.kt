package com.tick.taku.notificationwatcher.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class RoomInfoEntity(@ColumnInfo(name = RoomEntity.PRIMARY_KEY) val id: String,
                          val name: String,
                          @Embedded(prefix = "message_") val latestMessage: MessageEntity)