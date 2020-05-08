package com.tick.taku.notificationwatcher.domain.repository.internal

import android.content.Context
import android.content.Intent
import com.linecorp.linesdk.api.LineApiClientBuilder
import com.linecorp.linesdk.auth.LineLoginApi
import com.linecorp.linesdk.auth.LineLoginResult
import com.tick.taku.android.corecomponent.ktx.guard
import com.tick.taku.notificationwatcher.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(context: Context): AccountRepository {

    private val lineApiClient = LineApiClientBuilder(context, AccountRepository.LINE_CHANNEL_ID).build()

    private val accountInfo: Channel<String> = Channel(Channel.CONFLATED)

    @UseExperimental(kotlinx.coroutines.FlowPreview::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun accountInfo(): Flow<String> =
        accountInfo.consumeAsFlow()
            .onStart { getProfile() }

    private suspend fun getProfile() {
        withContext(Dispatchers.IO) {
            val account = lineApiClient.profile.takeIf { it.isSuccess }?.responseData?.displayName guard {
                Timber.e("Failed for getting profile.")
                ""
            }
            accountInfo.offer(account)
        }
    }

    override suspend fun onAccountLinkResult(data: Intent?) {
        val result = LineLoginApi.getLoginResultFromIntent(data).takeIf { it.isSuccess } guard {
            Timber.e("Failed for account link.")
            return
        }
        onObtainedProfile(result)
    }

    private fun onObtainedProfile(result: LineLoginResult) {
        result.lineProfile?.let {
            accountInfo.offer(it.displayName)
        }
    }

}