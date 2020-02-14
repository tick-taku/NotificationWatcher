package com.tick.taku.notificationwatcher.view.message.viewmodel

import androidx.lifecycle.ViewModel
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository

class MessageViewModel(private val repository: NotificationRepository): ViewModel()