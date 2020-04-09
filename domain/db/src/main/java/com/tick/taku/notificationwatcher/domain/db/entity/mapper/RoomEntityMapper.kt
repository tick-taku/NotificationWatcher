package com.tick.taku.notificationwatcher.domain.db.entity.mapper

import android.app.Notification
import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import com.tick.taku.notificationwatcher.domain.db.entity.internal.MessageEntityImpl
import com.tick.taku.notificationwatcher.domain.db.entity.internal.RoomEntityImpl
import com.tick.taku.notificationwatcher.domain.db.entity.internal.UserEntityImpl

private const val ROOM_ID = "line.chat.id"

private const val MESSAGE_ID = "line.message.id"

fun Notification.toEntities(context: Context): Triple<RoomEntityImpl, UserEntityImpl, MessageEntityImpl> =
    extras.let {
        val userName = it.getString(Notification.EXTRA_TITLE) ?: "Empty user"
        val room = RoomEntityImpl(
            id = it.getString(ROOM_ID) ?: "",
            name = it.getString(Notification.EXTRA_SUB_TEXT) ?: userName
        )

        // TODO: User id
        val user = UserEntityImpl(
            id = room.id + userName.hashCode(),
            name = userName,
            icon = getLargeIcon().loadDrawable(context).toBitmap()
        )

        val message = MessageEntityImpl(
            id = it.getString(MESSAGE_ID) ?: "",
            roomId = room.id,
            userId = user.id,
            message = it.getString(Notification.EXTRA_TEXT) ?: "Empty message",
            date = `when`
        )

        Triple(room, user, message)
    }