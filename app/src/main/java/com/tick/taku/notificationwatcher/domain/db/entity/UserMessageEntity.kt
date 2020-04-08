package com.tick.taku.notificationwatcher.domain.db.entity

import androidx.room.Embedded

data class UserMessageEntity(@Embedded val user: UserEntity,
                             @Embedded val message: MessageEntity)