package com.tick.taku.notificationwatcher.domain.db

import android.app.Notification
import android.content.Context
import com.tick.taku.notificationwatcher.domain.db.dao.MessageDao
import com.tick.taku.notificationwatcher.domain.db.dao.RoomDao
import com.tick.taku.notificationwatcher.domain.db.dao.UserDao

interface NotificationDatabase {

    fun roomDao(): RoomDao
    fun messageDao(): MessageDao
    fun userDao(): UserDao

    suspend fun saveFromNotification(context: Context, notification: Notification)

}