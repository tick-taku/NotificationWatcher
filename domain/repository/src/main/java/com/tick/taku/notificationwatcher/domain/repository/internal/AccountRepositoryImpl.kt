package com.tick.taku.notificationwatcher.domain.repository.internal

import android.content.Intent
import com.tick.taku.notificationwatcher.domain.api.account.AccountClient
import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity
import com.tick.taku.notificationwatcher.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(private val client: AccountClient): AccountRepository {

    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    private val accountInfo: ConflatedBroadcastChannel<AccountEntity> = ConflatedBroadcastChannel()

    @UseExperimental(kotlinx.coroutines.FlowPreview::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun accountInfo(): Flow<AccountEntity> =
        accountInfo.asFlow()
            .onStart { getProfile() }

    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    private suspend fun getProfile() {
        withContext(Dispatchers.IO) {
            accountInfo.offer(client.obtainProfile())
        }
    }

    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override suspend fun onAccountLinkResult(data: Intent?) {
        accountInfo.offer(client.onAccountLinked(data))
    }

}