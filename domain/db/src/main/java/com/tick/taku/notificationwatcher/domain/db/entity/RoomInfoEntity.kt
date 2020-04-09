package com.tick.taku.notificationwatcher.domain.db.entity

import android.graphics.Bitmap
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz

interface RoomInfoEntity {
    val id: String
    val name: String
    val latestMessage: LatestMessage
}

interface LatestMessage {
    val message: String
    val date: Long
    val icon: Bitmap

    fun localTime(): DateTimeTz = DateTime(date).local
}