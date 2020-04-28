package com.tick.taku.notificationwatcher.domain.db.dao

import androidx.room.*
import com.tick.taku.notificationwatcher.domain.db.base.DataAccessObject
import com.tick.taku.notificationwatcher.domain.db.internal.entity.RoomEntityImpl
import com.tick.taku.notificationwatcher.domain.db.internal.entity.RoomInfoEntityImpl
import kotlinx.coroutines.flow.Flow

@Dao internal abstract class RoomDao: DataAccessObject<RoomEntityImpl> {

    @Query("DELETE FROM room WHERE room_id = :id")
    abstract suspend fun deleteById(id: String)

    @Transaction
    @Query("SELECT room.*, message.message as latest_message, message.date as latest_date, user.user_icon as latest_icon " +
            "FROM room INNER JOIN user ON user.user_id = message.message_user_id " +
            "INNER JOIN message ON message.room_id = room.room_id " +
            "WHERE date = (SELECT MAX(date) FROM message AS latest_message WHERE latest_message.room_id = message.room_id) " +
            "ORDER BY latest_date DESC")
    abstract fun observeInfo(): Flow<List<RoomInfoEntityImpl>>

    @Query("SELECT COUNT(*) FROM room WHERE room_id = :id LIMIT 1")
    protected abstract fun isExistsRecord(id: String): Int

    /**
     * Insert or Update by entity existence
     *
     * @param room Room Entity
     */
    suspend fun insertOrUpdate(room: RoomEntityImpl) {
        if (isExistsRecord(room.id) == 0)
            insertIgnore(room)
        else
            update(room)
    }

}