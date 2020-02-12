package com.tick.taku.notificationwatcher.domain.db.dao

import androidx.room.*
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity

@Dao abstract class RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(room: RoomEntity)

    @Query("DELETE FROM room WHERE room_id = :id")
    abstract fun deleteById(id: String)

    @Query("SELECT * FROM room ORDER BY latest_update asc")
    abstract fun findAll(): List<Room>

}