package com.tick.taku.notificationwatcher.domain.db

import android.app.Notification
import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import kotlinx.coroutines.flow.Flow

interface NotificationDatabase {

    suspend fun saveFromNotification(context: Context, notification: Notification)

    // ----- Room -----

    fun observeRooms(): Flow<List<RoomInfoEntity>>
    suspend fun deleteRoom(id: String)

    // ----- Message -----
    fun observeMessages(roomId: String): Flow<List<UserMessageEntity>>
    suspend fun deleteMessage(id: String)
    suspend fun deleteMessageBefore(howOld: Int)

}