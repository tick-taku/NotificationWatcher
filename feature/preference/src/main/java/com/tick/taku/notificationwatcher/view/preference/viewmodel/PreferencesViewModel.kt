package com.tick.taku.notificationwatcher.view.preference.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tick.taku.notificationwatcher.domain.db.NotificationDatabase
import javax.inject.Inject

class PreferencesViewModel @Inject constructor(private val db: NotificationDatabase): ViewModel() {

    private val _isEnabledAutoDelete: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEnabledAutoDelete: LiveData<Boolean> = _isEnabledAutoDelete

    fun setIsEnabledAutoDelete(isEnabled: Boolean) {
        _isEnabledAutoDelete.postValue(isEnabled)
    }

}