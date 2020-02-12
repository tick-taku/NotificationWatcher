package com.tick.taku.notificationwatcher.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class RoomEntity(@PrimaryKey @ColumnInfo(name = PRIMARY_KEY) val id: String,
                      val user: String,
                      @ColumnInfo(name = "latest_message") val latestMessage: String,
                      @ColumnInfo(name = "latest_update") val latestUpdate: String) {
    companion object {
        const val PRIMARY_KEY = "room_id"
    }
}