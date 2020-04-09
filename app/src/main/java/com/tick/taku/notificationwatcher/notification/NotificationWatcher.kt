package com.tick.taku.notificationwatcher.notification

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import com.tick.taku.notificationwatcher.domain.repository.internal.NotificationRepositoryImpl
import com.tick.taku.notificationwatcher.view.DatabaseTmp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class NotificationWatcher: NotificationListenerService(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    // TODO: DI
    private val repository: NotificationRepository by lazy {
        NotificationRepositoryImpl(DatabaseTmp.db!!)
    }

    override fun onListenerConnected() {
        super.onListenerConnected()

        Timber.d("------------- NotificationWatcher#onListenerConnected. -------------")
        launch {
            activeNotifications
                .forEach {
                    repository.saveNotification(applicationContext, it)
                }
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        Timber.d("------------- NotificationWatcher#onNotificationPosted. -------------")
        launch {
            sbn?.let {
                repository.saveNotification(applicationContext, it)
            }
        }
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()

        Timber.d("------------- NotificationWatcher#onListenerDisconnected. -------------")
    }

}