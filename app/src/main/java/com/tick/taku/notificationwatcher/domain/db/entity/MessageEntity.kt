package com.tick.taku.notificationwatcher.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.soywiz.klock.DateTime
import com.tick.taku.notificationwatcher.domain.db.base.DaoEntity

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
                         val user: String,
                         val message: String,
                         val date: Long): DaoEntity {

    fun dateTime(): DateTime = DateTime(date)
}