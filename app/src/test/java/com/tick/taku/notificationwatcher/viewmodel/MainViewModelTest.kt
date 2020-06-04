package com.tick.taku.notificationwatcher.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.tick.taku.android.corecomponent.test.TestObserver
import com.tick.taku.notificationwatcher.Dummies
import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity
import com.tick.taku.notificationwatcher.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Rule

@UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: AccountRepository by lazy {
        mock<AccountRepository> {
            on { accountInfo() } doReturn flow { emit(Dummies.accountInfo) }
        }
    }

    private val viewModel: MainViewModel by lazy {
        MainViewModel(repository)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getAccountTest() {
        val testObserver = TestObserver<AccountEntity>()
        viewModel.account.observeForever(testObserver)

        testObserver.await()

        Truth.assertThat(testObserver.values.firstOrNull()).isEqualTo(Dummies.accountInfo)
    }

}