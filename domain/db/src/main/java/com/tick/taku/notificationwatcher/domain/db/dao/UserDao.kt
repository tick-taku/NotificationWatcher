package com.tick.taku.notificationwatcher.domain.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.tick.taku.notificationwatcher.domain.db.base.DataAccessObject
import com.tick.taku.notificationwatcher.domain.db.internal.entity.UserEntityImpl

@Dao internal abstract class UserDao: DataAccessObject<UserEntityImpl> {

    @Query("SELECT COUNT(*) FROM user WHERE user_id = :id LIMIT 1")
    protected abstract fun isExistsRecord(id: String): Int

    /**
     * Insert or Update by entity existence
     *
     * @param room Room Entity
     */
    suspend fun insertOrUpdate(room: UserEntityImpl) {
        if (isExistsRecord(room.id) == 0)
            insertIgnore(room)
        else
            update(room)
    }

}