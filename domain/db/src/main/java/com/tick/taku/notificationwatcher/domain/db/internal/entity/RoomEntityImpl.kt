package com.tick.taku.notificationwatcher.domain.db.internal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tick.taku.notificationwatcher.domain.db.base.DaoEntity
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity

@Entity(tableName = "room")
internal data class RoomEntityImpl(@PrimaryKey @ColumnInfo(name = PRIMARY_KEY) override val id: String,
                                   override val name: String): RoomEntity, DaoEntity {
    companion object {
        const val PRIMARY_KEY = "room_id"
    }
}