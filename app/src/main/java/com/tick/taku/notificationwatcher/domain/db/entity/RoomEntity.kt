package com.tick.taku.notificationwatcher.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tick.taku.notificationwatcher.domain.db.base.DaoEntity

@Entity(tableName = "room")
data class RoomEntity(@PrimaryKey @ColumnInfo(name = PRIMARY_KEY) val id: String,
                      val name: String): DaoEntity {
    companion object {
        const val PRIMARY_KEY = "room_id"
    }
}