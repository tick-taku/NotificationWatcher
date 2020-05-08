package com.tick.taku.notificationwatcher.domain.api.account

import android.content.Intent

interface AccountClient {

    companion object {
        const val LINE_CHANNEL_ID = ""
    }

    fun obtainProfile(): String

    fun onAccountLinked(data: Intent?): String

}