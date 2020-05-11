package com.tick.taku.notificationwatcher.domain.api.account

import android.content.Intent
import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity

interface AccountClient {

    fun obtainProfile(): AccountEntity

    fun onAccountLinked(data: Intent?): AccountEntity

}