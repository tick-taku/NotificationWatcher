package com.tick.taku.notificationwatcher.domain.db.dao

import androidx.room.*
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity

@Dao abstract class RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(room: RoomEntity)

    @Update
    abstract fun update(room: RoomEntity)

    @Query("DELETE FROM room WHERE room_id = :id")
    abstract fun deleteById(id: String)

    @Query("SELECT * FROM room ORDER BY latest_update asc")
    abstract fun findAll(): List<RoomEntity>

    @Query("SELECT COUNT(*) FROM room WHERE room_id = :id")
    protected abstract fun isExistsRecord(id: String): Int

    /**
     * Insert or Update by entity existence
     *
     * @param room Room Entity
     */
    fun insertOrUpdate(room: RoomEntity) {
        if (isExistsRecord(room.id) == 0)
            insert(room)
        else
            update(room)
    }

}