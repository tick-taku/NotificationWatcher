package com.tick.taku.notificationwatcher.domain.db.internal

import android.app.Notification
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soywiz.klock.DateTime
import com.soywiz.klock.months
import com.tick.taku.notificationwatcher.domain.db.NotificationDatabase
import com.tick.taku.notificationwatcher.domain.db.base.BitmapConverter
import com.tick.taku.notificationwatcher.domain.db.dao.MessageDao
import com.tick.taku.notificationwatcher.domain.db.dao.RoomDao
import com.tick.taku.notificationwatcher.domain.db.dao.UserDao
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import com.tick.taku.notificationwatcher.domain.db.internal.entity.MessageEntityImpl
import com.tick.taku.notificationwatcher.domain.db.internal.entity.RoomEntityImpl
import com.tick.taku.notificationwatcher.domain.db.internal.entity.UserEntityImpl
import com.tick.taku.notificationwatcher.domain.db.entity.mapper.toEntities
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Database(entities = [MessageEntityImpl::class, RoomEntityImpl::class, UserEntityImpl::class], version = 1)
@TypeConverters(BitmapConverter::class)
internal abstract class NotificationDatabaseImpl: RoomDatabase(), NotificationDatabase {

    // ----- Dao ------

    internal abstract fun roomDao(): RoomDao
    internal abstract fun messageDao(): MessageDao
    internal abstract fun userDao(): UserDao

    // ----- Operation -----

    override suspend fun saveFromNotification(context: Context, notification: Notification) {
        val (room, user, message) = notification.toEntities(context)

        roomDao().insertOrUpdate(room)
        userDao().insertOrUpdate(user)
        messageDao().insert(message)
    }

    override fun observeRooms(): Flow<List<RoomInfoEntity>> = roomDao().observeInfo()
    override suspend fun deleteRoom(id: String) {
        roomDao().deleteById(id)
    }

    override fun observeMessages(roomId: String): Flow<List<UserMessageEntity>> = messageDao().observe(roomId)
    override suspend fun deleteMessage(id: String) {
        messageDao().deleteById(id)
    }

    override suspend fun deleteMessageBefore(howOld: Int) {
        val specified = DateTime.now() - howOld.months
        messageDao().deleteBefore(specified.unixMillisLong)

        Timber.d("Delete message before ${specified.toString("yyyy/MM/dd HH:mm:ss")}.")
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