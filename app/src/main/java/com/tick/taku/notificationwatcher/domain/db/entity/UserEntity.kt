package com.tick.taku.notificationwatcher.domain.db.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tick.taku.notificationwatcher.domain.db.base.DaoEntity

@Entity(tableName = "user")
data class UserEntity(@PrimaryKey @ColumnInfo(name = PRIMARY_KEY) val id: String,
                      @ColumnInfo(name = "user_name") val name: String,
                      @ColumnInfo(name = "user_icon") val icon: Bitmap): DaoEntity {
    companion object {
        const val PRIMARY_KEY = "user_id"
    }
}