package com.tick.taku.notificationwatcher.domain.db.dao

import androidx.room.*
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao abstract class MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(message: MessageEntity)

    @Query("DELETE FROM message WHERE message_id = :id")
    abstract suspend fun deleteById(id: String)

    @Query("SELECT * FROM message ORDER BY date asc")
    abstract fun observe(): Flow<List<MessageEntity>>

    @Query("SELECT message_id FROM message ORDER BY message_id desc LIMIT 1")
    abstract suspend fun findLatestId(): String

}