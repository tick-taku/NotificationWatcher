package com.tick.taku.notificationwatcher.domain.api.account

import android.content.Intent
import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity

interface AccountClient {

    companion object {
        const val LINE_CHANNEL_ID = ""
    }

    fun obtainProfile(): AccountEntity

    fun onAccountLinked(data: Intent?): AccountEntity

}