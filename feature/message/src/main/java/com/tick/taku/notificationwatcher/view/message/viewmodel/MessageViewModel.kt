package com.tick.taku.notificationwatcher.view.message.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.tick.taku.notificationwatcher.domain.db.entity.UserMessageEntity
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel @AssistedInject constructor(@Assisted private val roomId: String,
                                                   @Assisted private val repository: NotificationRepository): ViewModel() {

    val messageList: LiveData<Map<String, List<UserMessageEntity>>> by lazy {
        repository.messageList(roomId).asLiveData()
    }

    fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMessage(id)
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(roomId: String, repository: NotificationRepository): MessageViewModel
    }
}