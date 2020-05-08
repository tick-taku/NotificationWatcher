package com.tick.taku.notificationwatcher.domain.repository

import android.content.Intent
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    companion object {
        const val LINE_CHANNEL_ID = ""
    }

    fun accountInfo(): Flow<String>

    suspend fun onAccountLinkResult(data: Intent?)

}