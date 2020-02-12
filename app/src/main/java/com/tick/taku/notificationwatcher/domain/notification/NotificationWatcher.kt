package com.tick.taku.notificationwatcher.domain.notification

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.soywiz.klock.DateTime
import com.tick.taku.notificationwatcher.domain.db.NotificationDataBase
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class NotificationWatcher: NotificationListenerService(), CoroutineScope {

    companion object {

        private val FILTERS = listOf("jp.naver.line.android")

        private const val ROOM_ID = "line.chat.id"

        private const val MESSAGE_ID = "line.message.id"

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
            Timber.d("Save message to db.")

            val (room, message) = notification.extras.let {
                val now = DateTime.nowLocal().toString("yyyyMMddHHmmss")

                val room = RoomEntity(
                    id = it.getString(ROOM_ID) ?: "",
                    user = it.getString(Notification.EXTRA_TITLE) ?: "Empty user",
                    latestMessage = it.getString(Notification.EXTRA_TEXT) ?: "Empty message",
                    latestUpdate = now
                )
                val message = MessageEntity(
                    id = it.getString(MESSAGE_ID) ?: "",
                    roomId = room.id,
                    date = now,
                    message = room.latestMessage
                )
                room to message
            }

            database.roomDao().insertOrUpdate(room)
            database.messageDao().insert(message)

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