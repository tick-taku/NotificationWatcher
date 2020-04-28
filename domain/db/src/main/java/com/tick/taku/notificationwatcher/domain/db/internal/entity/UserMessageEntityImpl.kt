package com.tick.taku.notificationwatcher.domain.db.internal.entity

import androidx.room.Embedded
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import com.tick.taku.notificationwatcher.domain.db.internal.entity.MessageEntityImpl
import com.tick.taku.notificationwatcher.domain.db.internal.entity.UserEntityImpl

internal data class UserMessageEntityImpl(@Embedded override val user: UserEntityImpl,
                                          @Embedded override val message: MessageEntityImpl
): UserMessageEntity