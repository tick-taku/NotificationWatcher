package com.tick.taku.notificationwatcher.domain.repository.internal

import android.app.Notification
import android.content.Context
import android.service.notification.StatusBarNotification
import com.tick.taku.notificationwatcher.domain.db.NotificationDatabase
import com.tick.taku.notificationwatcher.domain.db.entity.*
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

internal class NotificationRepositoryImpl @Inject constructor(private val context: Context,
                                                              private val db: NotificationDatabase)
    : NotificationRepository {

    companion object {

        private val FILTERS = listOf("jp.naver.line.android")

        private const val HEADER_FORMAT = "yyyy/MM/dd (EE)"

    }

    override suspend fun saveNotification(sbn: StatusBarNotification) {
        if (FILTERS.contains(sbn.packageName)) {
            Timber.d("Save message to db.")

            saveMessage(context, sbn.notification)

            print(sbn.notification)
        }
    }

    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun roomList(): Flow<List<RoomInfoEntity>> =
        db.observeRooms().distinctUntilChanged()

    override suspend fun deleteRoom(id: String) {
        db.deleteRoom(id)
    }

    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun messageList(roomId: String): Flow<Map<String, List<UserMessageEntity>>> =
        db.observeMessages(roomId).distinctUntilChanged()
            .map { entity ->
                entity.groupBy { it.message.localTime().toString(HEADER_FORMAT) }
            }

    override suspend fun deleteMessage(id: String) {
        db.deleteMessage(id)
    }

    /**
     * Save message to db.
     *
     * @param notification Notification
     */
    private suspend fun saveMessage(context: Context, notification: Notification) {
        db.saveFromNotification(context, notification)
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