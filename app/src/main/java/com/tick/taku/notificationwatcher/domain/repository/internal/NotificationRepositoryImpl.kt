package com.tick.taku.notificationwatcher.domain.repository.internal

import android.app.Notification
import android.service.notification.StatusBarNotification
import com.soywiz.klock.DateTime
import com.tick.taku.notificationwatcher.domain.db.NotificationDataBase
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class NotificationRepositoryImpl(private val db: NotificationDataBase): NotificationRepository {

    companion object {

        private val FILTERS = listOf("jp.naver.line.android")

        private const val ROOM_ID = "line.chat.id"

        private const val MESSAGE_ID = "line.message.id"

    }

    override suspend fun saveNotification(sbn: StatusBarNotification) {
        if (FILTERS.contains(sbn.packageName)) {
            Timber.d("Save message to db.")

            saveMessage(sbn.notification)

            print(sbn.notification)
        }
    }

    override fun roomList(): Flow<List<RoomEntity>> = db.roomDao().findAll()

    /**
     * Save message to db.
     *
     * @param notification Notification
     */
    private fun saveMessage(notification: Notification) {
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

        db.roomDao().insertOrUpdate(room)
        db.messageDao().insert(message)
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