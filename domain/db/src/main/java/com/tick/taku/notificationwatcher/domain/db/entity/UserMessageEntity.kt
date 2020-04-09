package com.tick.taku.notificationwatcher.domain.db.entity

interface UserMessageEntity {
    val user: UserEntity
    val message: MessageEntity
}