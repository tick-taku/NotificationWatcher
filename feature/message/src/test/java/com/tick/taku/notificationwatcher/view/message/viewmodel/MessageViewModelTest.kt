package com.tick.taku.notificationwatcher.view.message.viewmodel

import android.content.Context
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.tick.taku.android.corecomponent.test.ViewModelTestRule
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import com.tick.taku.notificationwatcher.domain.repository.MessageRepository
import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.view.mock.Dummies
import kotlinx.coroutines.flow.flow
import org.junit.Test
import org.junit.Rule

@UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class MessageViewModelTest {

    companion object {
        private const val GROUPING_FORMAT = "yyyyMMdd"
    }

    @get:Rule val viewModelRule = ViewModelTestRule()

    private val repository: MessageRepository by lazy {
        mock<MessageRepository> {
            on { messageList("0001") } doReturn flow { emit(Dummies.messageList) }
            on { isShowUrlPreview() } doReturn flow { emit(true) }
        }
    }

    private val context: Context by lazy {
        mock<Context> {
            on { getString(R.string.grouping_date_format) } doReturn GROUPING_FORMAT
        }
    }

    private val viewModel: MessageViewModel by lazy {
        MessageViewModel("0001", context, repository)
    }

    @Test
    fun getMessageListTest() {
        val testObserver = mock<Observer<Map<String, List<UserMessageEntity>>>>()
        viewModel.messageList.observeForever(testObserver)

        Dummies.messageList.groupBy { it.message.localTime().toString(GROUPING_FORMAT) }.let {
            verify(testObserver).onChanged(it)
        }
    }

    @Test
    fun isShowUrlPreviewTest() {
        val testObserver = mock<Observer<Boolean>>()
        viewModel.isShowUrlPreview.observeForever(testObserver)

        verify(testObserver).onChanged(true)
    }
}