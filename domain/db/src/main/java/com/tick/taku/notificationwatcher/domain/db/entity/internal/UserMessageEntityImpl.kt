package com.tick.taku.notificationwatcher.domain.db.entity.internal

import androidx.room.Embedded
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity

data class UserMessageEntityImpl(@Embedded override val user: UserEntityImpl,
                                 @Embedded override val message: MessageEntityImpl): UserMessageEntity