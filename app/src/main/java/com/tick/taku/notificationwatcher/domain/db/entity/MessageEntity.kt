package com.tick.taku.notificationwatcher.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity(@PrimaryKey @ColumnInfo(name = "message_id") val id: Int,
                         val date: String,
                         val user: String,
                         val message: String)