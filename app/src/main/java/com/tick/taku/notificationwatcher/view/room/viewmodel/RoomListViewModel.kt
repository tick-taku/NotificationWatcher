package com.tick.taku.notificationwatcher.view.room.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomListViewModel(private val repository: NotificationRepository): ViewModel() {

    val roomList: LiveData<List<RoomEntity>> by lazy {
        repository.roomList().asLiveData()
    }

    fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRoom(id)
        }
    }

}