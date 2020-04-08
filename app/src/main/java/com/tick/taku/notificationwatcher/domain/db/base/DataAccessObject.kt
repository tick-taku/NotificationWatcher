package com.tick.taku.notificationwatcher.domain.db.base

import androidx.room.*

interface DataAccessObject <T: DaoEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnore(entity: T)

    @Update suspend fun update(entity: T)

    @Delete suspend fun delete(entity: T)

}