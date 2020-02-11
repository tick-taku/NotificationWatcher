package com.tick.taku.notificationwatcher.domain.notification

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.soywiz.klock.DateTime
import com.tick.taku.notificationwatcher.domain.db.NotificationDataBase
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class NotificationWatcher: NotificationListenerService(), CoroutineScope {

    companion object {
        private val FILTERS = listOf("jp.naver.line.android")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private val database: NotificationDataBase by lazy {
        NotificationDataBase.getInstance(applicationContext)
    }

    override fun onListenerConnected() {
        super.onListenerConnected()

        Timber.d("------------- NotificationWatcher#onListenerConnected. -------------")
        activeNotifications
            .filter { FILTERS.contains(it.packageName) }
            .forEach { saveMessage(it.notification) }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        Timber.d("------------- NotificationWatcher#onNotificationPosted. -------------")
        Timber.d("${sbn?.packageName}")
        sbn?.let {
            if (FILTERS.contains(it.packageName))
                saveMessage(it.notification)
        }
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()

        Timber.d("------------- NotificationWatcher#onListenerDisconnected. -------------")
    }

    /**
     * Save message to db.
     *
     * @param notification Notification
     */
    private fun saveMessage(notification: Notification) {
        launch {
            val dao = database.messageDao()

            val (user, message) = notification.extras.let {
                (it.getString(Notification.EXTRA_TITLE) ?: "") to (it.getString(Notification.EXTRA_TEXT) ?: "")
            }

            val entity = MessageEntity(
                id = dao.findLatestId() + 1,
                date = DateTime.nowLocal().toString("yyyyMMddHHmmss"),
                user = user,
                message = message
            )
            dao.insert(entity)

            print(notification)
        }
    }

    /**
     * Output notification message.
     *
     * @param notification Notification
     */
    private fun print(notification: Notification) {
        notification.extras.run {
            val title = getString(Notification.EXTRA_TITLE) ?: ""
            val message = getString(Notification.EXTRA_TEXT) ?: ""
            Timber.d("Active notification: <Title - $title, Text - $message>")
        }
    }

}