package com.tick.taku.notificationwatcher.domain.repository

import android.content.Context
import android.service.notification.StatusBarNotification
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    /**
     * Save notification to db.
     */
    suspend fun saveNotification(context: Context, sbn: StatusBarNotification)

    /**
     * Load room list.
     */
    fun roomList(): Flow<List<RoomInfoEntity>>

    /**
     * Delete room entity by id.
     */
    suspend fun deleteRoom(id: String)

    /**
     * Load message list.
     */
    fun messageList(roomId: String): Flow<List<UserMessageEntity>>

    /**
     * Delete message entity by id.
     */
    suspend fun deleteMessage(id: String)

}