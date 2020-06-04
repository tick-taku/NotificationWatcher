package com.tick.taku.notificationwatcher.view.preference.viewmodel

import androidx.lifecycle.Observer
import androidx.work.*
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.tick.taku.android.corecomponent.test.ViewModelTestRule
import com.tick.taku.notificationwatcher.view.preference.worker.AutoDeletionWorker
import org.junit.Test
import org.junit.Rule
import java.util.concurrent.TimeUnit

class PreferencesViewModelTest {

    @get:Rule val viewModelRule = ViewModelTestRule()

    private val workManager: WorkManager = mock {
        on {
            val request = PeriodicWorkRequestBuilder<AutoDeletionWorker>(1, TimeUnit.DAYS).build()
            enqueueUniquePeriodicWork(AutoDeletionWorker.TAG, ExistingPeriodicWorkPolicy.KEEP, request)
        } doReturn mock<Operation>()
        on { cancelAllWorkByTag(AutoDeletionWorker.TAG) } doReturn mock<Operation>()
    }

    private val viewModel: PreferencesViewModel by lazy {
        PreferencesViewModel(workManager)
    }

    @Test fun isEnabledAutoDeleteTest() {
        viewModel.isEnabledAutoDelete.observeForever(mock<Observer<Boolean>>())

        viewModel.setIsEnabledAutoDelete(false)
        Truth.assertThat(viewModel.isEnabledAutoDelete.value).isFalse()

        viewModel.setIsEnabledAutoDelete(true)
        Truth.assertThat(viewModel.isEnabledAutoDelete.value).isTrue()
    }
}