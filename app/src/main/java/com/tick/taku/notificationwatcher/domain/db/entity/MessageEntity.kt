package com.tick.taku.notificationwatcher.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "message",
        primaryKeys = ["message_id", "room_id"],
        foreignKeys = [
            ForeignKey(
                entity = RoomEntity::class,
                parentColumns = [RoomEntity.PRIMARY_KEY],
                childColumns = ["room_id"],
                onDelete = ForeignKey.CASCADE
            )
        ])
data class MessageEntity(@ColumnInfo(name = "message_id") val id: String,
                         @ColumnInfo(name = "room_id") val roomId: String,
                         val date: String,
                         val message: String)