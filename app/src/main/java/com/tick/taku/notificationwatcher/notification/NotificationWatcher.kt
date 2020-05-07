package com.tick.taku.notificationwatcher.notification

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.tick.taku.notificationwatcher.domain.repository.MessageRepository
import dagger.Module
import dagger.android.AndroidInjection
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NotificationWatcher: NotificationListenerService(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    @Inject lateinit var repository: MessageRepository

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onListenerConnected() {
        super.onListenerConnected()

        Timber.d("------------- NotificationWatcher#onListenerConnected. -------------")
        launch {
            activeNotifications
                .forEach {
                    repository.saveNotification(it)
                }
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        Timber.d("------------- NotificationWatcher#onNotificationPosted. -------------")
        launch {
            sbn?.let {
                repository.saveNotification(it)
            }
        }
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()

        Timber.d("------------- NotificationWatcher#onListenerDisconnected. -------------")
    }

}

@Module
abstract class NotificationWatcherModule {

    @ContributesAndroidInjector
    abstract fun contributeNotificationService(): NotificationWatcher

}