package com.tick.taku.notificationwatcher.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.linecorp.linesdk.auth.LineLoginApi
import com.linecorp.linesdk.auth.LineLoginResult
import com.tick.taku.android.corecomponent.ktx.guard
import onactivityresult.OnActivityResult
import timber.log.Timber

class MainViewModel: ViewModel() {

    companion object {
        const val ACCOUNT_LINK_RESULT = 999
    }

    private val _account: MutableLiveData<String> = MutableLiveData("")
    val account: LiveData<String> = _account

    @Suppress("unused")
    @OnActivityResult(requestCode = ACCOUNT_LINK_RESULT, resultCodes = [Activity.RESULT_OK])
    fun onAccountLinkResult(data: Intent?) {
        val result = LineLoginApi.getLoginResultFromIntent(data).takeIf { it.isSuccess } guard {
            Timber.e("Failed for account link.")
            return
        }
        onObtainedProfile(result)
    }

    private fun onObtainedProfile(result: LineLoginResult) {
        result.lineProfile?.displayName?.let {
            _account.postValue(it)
        }
    }

}