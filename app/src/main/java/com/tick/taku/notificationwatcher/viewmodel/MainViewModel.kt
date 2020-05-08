package com.tick.taku.notificationwatcher.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.*
import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity
import com.tick.taku.notificationwatcher.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import onactivityresult.OnActivityResult
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: AccountRepository): ViewModel() {

    companion object {
        const val ACCOUNT_LINK_RESULT = 999
    }

    val account: LiveData<AccountEntity> by lazy {
        repository.accountInfo().asLiveData(Dispatchers.IO)
    }

    @Suppress("unused")
    @OnActivityResult(requestCode = ACCOUNT_LINK_RESULT, resultCodes = [Activity.RESULT_OK])
    fun onAccountLinkResult(data: Intent?) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.onAccountLinkResult(data)
        }
    }

}