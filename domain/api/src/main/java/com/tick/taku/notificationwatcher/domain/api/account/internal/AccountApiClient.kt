package com.tick.taku.notificationwatcher.domain.api.account.internal

import android.content.Context
import android.content.Intent
import com.linecorp.linesdk.LineProfile
import com.linecorp.linesdk.api.LineApiClient
import com.linecorp.linesdk.api.LineApiClientBuilder
import com.linecorp.linesdk.auth.LineLoginApi
import com.tick.taku.android.corecomponent.ktx.guard
import com.tick.taku.notificationwatcher.domain.BuildConfig
import com.tick.taku.notificationwatcher.domain.api.account.AccountClient
import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity
import timber.log.Timber
import javax.inject.Inject

internal class AccountApiClient @Inject constructor(private val context: Context): AccountClient {

    private val lineApiClient: LineApiClient by lazy {
        LineApiClientBuilder(context, BuildConfig.LINE_CHANNEL_ID).build()
    }

    override fun obtainProfile(): AccountEntity {
        val result = lineApiClient.profile.takeIf { it.isSuccess }?.responseData guard {
            Timber.e("Failed for getting profile.")
            return defaultAccount()
        }
        return extractAccount(result)
    }

    override fun onAccountLinked(data: Intent?): AccountEntity {
        val result = LineLoginApi.getLoginResultFromIntent(data).takeIf { it.isSuccess }?.lineProfile guard {
            Timber.e("Failed for account link.")
            return defaultAccount()
        }
        return extractAccount(result)
    }

    private fun defaultAccount() = AccountEntityImpl("", "", "")
    private fun extractAccount(profile: LineProfile) =
        AccountEntityImpl(profile.displayName, profile.statusMessage ?: "", profile.pictureUrl?.toString() ?: "")

}