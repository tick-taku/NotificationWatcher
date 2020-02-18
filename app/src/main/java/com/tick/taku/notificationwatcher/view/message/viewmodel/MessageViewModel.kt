package com.tick.taku.notificationwatcher.view.message.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel(private val repository: NotificationRepository,
                       private val roomId: String): ViewModel() {

    val messageList: LiveData<List<MessageEntity>> by lazy {
        repository.messageList(roomId).asLiveData()
    }

    fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMessage(id)
        }
    }

}