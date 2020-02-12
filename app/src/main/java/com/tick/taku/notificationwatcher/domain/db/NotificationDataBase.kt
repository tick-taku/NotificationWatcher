package com.tick.taku.notificationwatcher.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tick.taku.notificationwatcher.domain.db.dao.MessageDao
import com.tick.taku.notificationwatcher.domain.db.dao.RoomDao
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity

@Database(entities = [MessageEntity::class, RoomEntity::class], version = 1)
abstract class NotificationDataBase: RoomDatabase() {

    abstract fun roomDao(): RoomDao
    abstract fun messageDao(): MessageDao

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