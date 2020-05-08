package com.tick.taku.notificationwatcher.domain.api.account.internal

import android.content.Context
import android.content.Intent
import com.linecorp.linesdk.api.LineApiClient
import com.linecorp.linesdk.api.LineApiClientBuilder
import com.linecorp.linesdk.auth.LineLoginApi
import com.tick.taku.android.corecomponent.ktx.guard
import com.tick.taku.notificationwatcher.domain.api.account.AccountClient
import timber.log.Timber
import javax.inject.Inject

internal class AccountApiClient @Inject constructor(private val context: Context): AccountClient {

    private val lineApiClient: LineApiClient by lazy {
        LineApiClientBuilder(context, AccountClient.LINE_CHANNEL_ID).build()
    }

    override fun obtainProfile(): String {
        return lineApiClient.profile.takeIf { it.isSuccess }?.responseData?.displayName guard {
            Timber.e("Failed for getting profile.")
            ""
        }
    }

    override fun onAccountLinked(data: Intent?): String {
        return LineLoginApi.getLoginResultFromIntent(data).takeIf { it.isSuccess }?.lineProfile?.displayName guard {
            Timber.e("Failed for account link.")
            ""
        }
    }

}