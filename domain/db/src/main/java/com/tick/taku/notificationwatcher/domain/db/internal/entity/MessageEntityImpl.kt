package com.tick.taku.notificationwatcher.domain.db.internal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.tick.taku.notificationwatcher.domain.db.base.DaoEntity
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity

@Entity(tableName = "message",
        primaryKeys = ["message_id", "room_id", "message_user_id"],
        foreignKeys = [
            ForeignKey(
                entity = RoomEntityImpl::class,
                parentColumns = [RoomEntityImpl.PRIMARY_KEY],
                childColumns = ["room_id"],
                onDelete = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = UserEntityImpl::class,
                parentColumns = [UserEntityImpl.PRIMARY_KEY],
                childColumns = ["message_user_id"],
                onDelete = ForeignKey.CASCADE
            )
        ])
internal data class MessageEntityImpl(@ColumnInfo(name = "message_id") override val id: String,
                                      @ColumnInfo(name = "room_id", index = true) override val roomId: String,
                                      @ColumnInfo(name = "message_user_id", index = true) override val userId: String,
                                      override val message: String,
                                      override val date: Long): MessageEntity, DaoEntity