package com.tick.taku.notificationwatcher.domain.api.account.internal

import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity

internal data class AccountEntityImpl(override val name: String,
                                      override val status: String,
                                      override val iconUrl: String): AccountEntity