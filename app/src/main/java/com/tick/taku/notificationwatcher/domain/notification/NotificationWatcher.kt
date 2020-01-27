package com.tick.taku.notificationwatcher.domain.notification

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import timber.log.Timber

class NotificationWatcher: NotificationListenerService() {

    override fun onListenerConnected() {
        super.onListenerConnected()

        Timber.d("------------- NotificationWatcher#onListenerConnected. -------------")
        activeNotifications.forEach {
            print(it.notification)
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        Timber.d("------------- NotificationWatcher#onNotificationPosted. -------------")
        Timber.d("${sbn?.packageName}")
        sbn?.notification?.let {
            print(it)
        }
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()

        Timber.d("------------- NotificationWatcher#onListenerDisconnected. -------------")
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