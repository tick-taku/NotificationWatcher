package com.tick.taku.notificationwatcher.domain.repository

import android.service.notification.StatusBarNotification

interface NotificationRepository {

    /**
     * Save notification to db.
     */
    suspend fun saveNotification(sbn: StatusBarNotification)

}