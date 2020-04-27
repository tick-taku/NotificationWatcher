package com.tick.taku.notificationwatcher.domain.db.internal

import android.app.Notification
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tick.taku.notificationwatcher.domain.db.NotificationDatabase
import com.tick.taku.notificationwatcher.domain.db.base.BitmapConverter
import com.tick.taku.notificationwatcher.domain.db.entity.internal.MessageEntityImpl
import com.tick.taku.notificationwatcher.domain.db.entity.internal.RoomEntityImpl
import com.tick.taku.notificationwatcher.domain.db.entity.internal.UserEntityImpl
import com.tick.taku.notificationwatcher.domain.db.entity.mapper.toEntities

@Database(entities = [MessageEntityImpl::class, RoomEntityImpl::class, UserEntityImpl::class], version = 1)
@TypeConverters(BitmapConverter::class)
internal abstract class NotificationDatabaseImpl: RoomDatabase(), NotificationDatabase {

    override suspend fun saveFromNotification(context: Context, notification: Notification) {
        val (room, user, message) = notification.toEntities(context)

        roomDao().insertOrUpdate(room)
        userDao().insertOrUpdate(user)
        messageDao().insert(message)
    }

    companion object {

        private const val NOTIFICATION_DATABASE_FILE = "notification.db"

        /**
         * Get shared DataBase instance
         *
         * @param context Context
         * @return Database instance
         */
        fun getInstance(context: Context): NotificationDatabaseImpl =
            Room.databaseBuilder(context, NotificationDatabaseImpl::class.java, NOTIFICATION_DATABASE_FILE).build()

    }

}