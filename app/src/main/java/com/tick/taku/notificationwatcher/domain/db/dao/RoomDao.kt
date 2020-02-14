package com.tick.taku.notificationwatcher.domain.db.dao

import androidx.room.*
import com.tick.taku.notificationwatcher.domain.db.base.DataAccessObject
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao abstract class RoomDao: DataAccessObject<RoomEntity> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertIgnore(room: RoomEntity)

    @Query("DELETE FROM room WHERE room_id = :id")
    abstract suspend fun deleteById(id: String)

    @Transaction
    @Query("SELECT room.*, message.message_id as message_message_id, message.room_id as message_room_id, message.message as message_message, message.date as message_date "
            + "FROM room INNER JOIN message ON message.room_id = room.room_id ORDER BY date DESC LIMIT 1")
    abstract fun observeInfo(): Flow<List<RoomInfoEntity>>

    @Query("SELECT COUNT(*) FROM room WHERE room_id = :id")
    protected abstract fun isExistsRecord(id: String): Int

    /**
     * Insert or Update by entity existence
     *
     * @param room Room Entity
     */
    suspend fun insertOrUpdate(room: RoomEntity) {
        if (isExistsRecord(room.id) == 0)
            insertIgnore(room)
        else
            update(room)
    }

}