package com.tick.taku.notificationwatcher.view.preference.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.tick.taku.notificationwatcher.view.preference.worker.AutoDeletionWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PreferencesViewModel @Inject constructor(private val workManager: WorkManager): ViewModel() {

    private val _isEnabledAutoDelete: MutableLiveData<Boolean> = MutableLiveData(true)
    val isEnabledAutoDelete: LiveData<Boolean> = _isEnabledAutoDelete

    fun setIsEnabledAutoDelete(isEnabled: Boolean) {
        _isEnabledAutoDelete.postValue(isEnabled)

        when (isEnabled) {
            true -> startDeletionWorker()
            false -> workManager.cancelAllWorkByTag(AutoDeletionWorker.TAG)
        }
    }

    private fun startDeletionWorker() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresDeviceIdle(true)

        PeriodicWorkRequestBuilder<AutoDeletionWorker>(1, TimeUnit.DAYS)
            .addTag(AutoDeletionWorker.TAG)
            .setInitialDelay(1, TimeUnit.DAYS)
            .setConstraints(constraints.build())
            .build()
            .enqueueWithTag(AutoDeletionWorker.TAG, ExistingPeriodicWorkPolicy.KEEP)
    }

    private fun PeriodicWorkRequest.enqueueWithTag(tag: String, periodicWorkPolicy: ExistingPeriodicWorkPolicy) {
        workManager.enqueueUniquePeriodicWork(tag, periodicWorkPolicy, this)
    }

}