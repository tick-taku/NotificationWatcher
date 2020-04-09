package com.tick.taku.notificationwatcher.domain.db.entity

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz

interface MessageEntity {
    val id: String
    val roomId: String
    val userId: String
    val message: String
    val date: Long

    fun localTime(): DateTimeTz = DateTime(date).local
}