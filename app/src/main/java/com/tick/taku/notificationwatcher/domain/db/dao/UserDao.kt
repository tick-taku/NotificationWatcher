package com.tick.taku.notificationwatcher.domain.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.tick.taku.notificationwatcher.domain.db.base.DataAccessObject
import com.tick.taku.notificationwatcher.domain.db.entity.UserEntity

@Dao abstract class UserDao: DataAccessObject<UserEntity> {

    @Query("SELECT COUNT(*) FROM room WHERE room_id = :id LIMIT 1")
    protected abstract fun isExistsRecord(id: String): Int

    /**
     * Insert or Update by entity existence
     *
     * @param room Room Entity
     */
    suspend fun insertOrUpdate(room: UserEntity) {
        if (isExistsRecord(room.id) == 0)
            insertIgnore(room)
        else
            update(room)
    }

}