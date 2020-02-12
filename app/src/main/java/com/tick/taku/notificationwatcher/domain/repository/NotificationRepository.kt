package com.tick.taku.notificationwatcher.domain.repository

import android.service.notification.StatusBarNotification
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    /**
     * Save notification to db.
     */
    suspend fun saveNotification(sbn: StatusBarNotification)

    /**
     * Load room list.
     */
    fun roomList(): Flow<List<RoomEntity>>

}