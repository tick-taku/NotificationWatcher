package com.tick.taku.notificationwatcher.domain.db.dao

import androidx.room.*
import com.tick.taku.notificationwatcher.domain.db.base.DataAccessObject
import com.tick.taku.notificationwatcher.domain.db.internal.entity.MessageEntityImpl
import com.tick.taku.notificationwatcher.domain.db.internal.entity.UserMessageEntityImpl
import kotlinx.coroutines.flow.Flow

@Dao internal abstract class MessageDao: DataAccessObject<MessageEntityImpl> {

    @Query("DELETE FROM message WHERE message_id = :id")
    abstract suspend fun deleteById(id: String)

    @Query("SELECT * FROM message INNER JOIN user ON user.user_id = message.message_user_id " +
            "WHERE message.room_id = :roomId ORDER BY message.date asc")
    abstract fun observe(roomId: String): Flow<List<UserMessageEntityImpl>>

    @Query("SELECT message_id FROM message ORDER BY message_id desc LIMIT 1")
    abstract suspend fun findLatestId(): String

}