package com.tick.taku.notificationwatcher.view.room.viewmodel

import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.tick.taku.android.corecomponent.test.ViewModelTestRule
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import com.tick.taku.notificationwatcher.domain.repository.MessageRepository
import com.tick.taku.notificationwatcher.view.mock.Dummies
import kotlinx.coroutines.flow.flow
import org.junit.Test
import org.junit.Rule

@UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class RoomListViewModelTest {

    @get:Rule val viewModelRule = ViewModelTestRule()

    private val repository: MessageRepository by lazy {
        mock<MessageRepository> {
            on { roomList() } doReturn flow { emit(Dummies.roomList) }
        }
    }

    private val viewModel: RoomListViewModel by lazy {
        RoomListViewModel(repository)
    }

    @Test
    fun roomListTest() {
        val testObserver = mock<Observer<List<RoomInfoEntity>>>()
        viewModel.roomList.observeForever(testObserver)

        verify(testObserver).onChanged(Dummies.roomList)
    }

}