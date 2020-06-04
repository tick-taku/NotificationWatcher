package com.tick.taku.notificationwatcher.viewmodel

import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.tick.taku.android.corecomponent.test.TestObserver
import com.tick.taku.android.corecomponent.test.ViewModelTestRule
import com.tick.taku.notificationwatcher.Dummies
import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity
import com.tick.taku.notificationwatcher.domain.repository.AccountRepository
import kotlinx.coroutines.flow.flow
import org.junit.Test
import org.junit.Rule

@UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule val viewModelRule = ViewModelTestRule()

    private val repository: AccountRepository by lazy {
        mock<AccountRepository> {
            on { accountInfo() } doReturn flow { emit(Dummies.accountInfo) }
        }
    }

    private val viewModel: MainViewModel by lazy {
        MainViewModel(repository)
    }

    @Test
    fun getAccountTest() {
        val testObserver = TestObserver<AccountEntity>()
        viewModel.account.observeForever(testObserver)

        testObserver.await()

        Truth.assertThat(testObserver.values.firstOrNull()).isEqualTo(Dummies.accountInfo)
    }

}