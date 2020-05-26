package com.tick.taku.notificationwatcher.view.message.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import com.tick.taku.notificationwatcher.domain.repository.MessageRepository
import com.tick.taku.notificationwatcher.view.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel @AssistedInject constructor(@Assisted private val roomId: String,
                                                   private val context: Context,
                                                   private val repository: MessageRepository): ViewModel() {

    val messageList: LiveData<Map<String, List<UserMessageEntity>>> by lazy {
        val format = context.getString(R.string.grouping_date_format)
        repository.messageList(roomId).asLiveData().map { entity ->
            entity.groupBy { it.message.localTime().toString(format) }
        }
    }

    val isShowUrlPreview: LiveData<Boolean> by lazy {
        repository.isShowUrlPreview().asLiveData()
    }

    val outgoingMessage: MutableLiveData<String> = MutableLiveData("")

    fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMessage(id)
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(roomId: String): MessageViewModel
    }
}