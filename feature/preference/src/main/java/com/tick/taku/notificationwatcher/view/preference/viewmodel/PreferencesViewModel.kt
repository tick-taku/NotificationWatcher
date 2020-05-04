package com.tick.taku.notificationwatcher.view.preference.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.tick.taku.notificationwatcher.view.preference.worker.AutoDeletionWorker
import javax.inject.Inject

class PreferencesViewModel @Inject constructor(private val workManager: WorkManager): ViewModel() {

    private val _isEnabledAutoDelete: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEnabledAutoDelete: LiveData<Boolean> = _isEnabledAutoDelete

    fun setIsEnabledAutoDelete(isEnabled: Boolean) {
        _isEnabledAutoDelete.postValue(isEnabled)

        when (isEnabled) {
            true -> startDeletionWorker()
            false -> workManager.cancelAllWork()
        }
    }

    private fun startDeletionWorker() {
        val request = OneTimeWorkRequestBuilder<AutoDeletionWorker>().build()
        workManager.enqueue(request)
    }

}