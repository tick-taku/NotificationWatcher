package com.tick.taku.notificationwatcher.domain.db

import android.app.Notification
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tick.taku.notificationwatcher.domain.db.base.BitmapConverter
import com.tick.taku.notificationwatcher.domain.db.dao.MessageDao
import com.tick.taku.notificationwatcher.domain.db.dao.RoomDao
import com.tick.taku.notificationwatcher.domain.db.dao.UserDao
import com.tick.taku.notificationwatcher.domain.db.entity.internal.MessageEntityImpl
import com.tick.taku.notificationwatcher.domain.db.entity.internal.RoomEntityImpl
import com.tick.taku.notificationwatcher.domain.db.entity.internal.UserEntityImpl
import com.tick.taku.notificationwatcher.domain.db.entity.mapper.toEntities

@Database(entities = [MessageEntityImpl::class, RoomEntityImpl::class, UserEntityImpl::class], version = 1)
@TypeConverters(BitmapConverter::class)
abstract class NotificationDataBase: RoomDatabase() {

    abstract fun roomDao(): RoomDao
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao

    suspend fun saveFromNotification(context: Context, notification: Notification) {
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
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, NotificationDataBase::class.java, NOTIFICATION_DATABASE_FILE).build()

    }

}