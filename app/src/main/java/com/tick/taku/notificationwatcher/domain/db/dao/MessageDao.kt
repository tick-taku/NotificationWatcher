package com.tick.taku.notificationwatcher.domain.db.dao

import androidx.room.*
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity

@Dao abstract class MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(message: MessageEntity)

    @Query("DELETE FROM message WHERE message_id = :id")
    abstract fun deleteById(id: Int)

    @Query("SELECT * FROM message ORDER BY date asc")
    abstract fun findAll(): List<MessageEntity>

    @Query("SELECT message_id FROM message ORDER BY message_id desc LIMIT 1")
    abstract fun findLatestId(): Int

}