package com.tick.taku.notificationwatcher

import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity

internal object Dummies {

    val accountInfo = object: AccountEntity {
        override val name: String = "name"
        override val iconUrl: String = "https://icon"
        override val status: String = "status"
    }

}