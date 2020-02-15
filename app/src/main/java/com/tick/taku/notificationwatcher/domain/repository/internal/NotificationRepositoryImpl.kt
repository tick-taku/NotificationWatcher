package com.tick.taku.notificationwatcher.domain.repository.internal

import android.app.Notification
import android.service.notification.StatusBarNotification
import com.soywiz.klock.DateTime
import com.tick.taku.notificationwatcher.domain.db.NotificationDataBase
import com.tick.taku.notificationwatcher.domain.db.entity.MessageEntity
import com.tick.taku.notificationwatcher.domain.db.entity.RoomEntity
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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

    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun roomList(): Flow<List<RoomInfoEntity>> =
        db.roomDao().observeInfo().distinctUntilChanged()

    override suspend fun deleteRoom(id: String) {
        db.roomDao().deleteById(id)
    }

    /**
     * Save message to db.
     *
     * @param notification Notification
     */
    private suspend fun saveMessage(notification: Notification) {
        val (room, message) = notification.extras.let {
            val room = RoomEntity(
                id = it.getString(ROOM_ID) ?: "",
                name = it.getString(Notification.EXTRA_TITLE) ?: "Empty user"
            )
            val message = MessageEntity(
                id = it.getString(MESSAGE_ID) ?: "",
                roomId = room.id,
                message = it.getString(Notification.EXTRA_TEXT) ?: "Empty message",
                date = DateTime.nowLocal().local.unixMillisLong
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