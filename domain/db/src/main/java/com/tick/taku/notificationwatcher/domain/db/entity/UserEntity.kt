package com.tick.taku.notificationwatcher.domain.db.entity

import android.graphics.Bitmap

interface UserEntity {
    val id: String
    val name: String
    val icon: Bitmap
}