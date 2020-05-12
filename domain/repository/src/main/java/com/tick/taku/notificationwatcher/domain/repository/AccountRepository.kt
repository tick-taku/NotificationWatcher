package com.tick.taku.notificationwatcher.domain.repository

import android.content.Intent
import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun accountInfo(): Flow<AccountEntity>

    suspend fun onAccountLinkResult(data: Intent?)

    suspend fun logout()

}