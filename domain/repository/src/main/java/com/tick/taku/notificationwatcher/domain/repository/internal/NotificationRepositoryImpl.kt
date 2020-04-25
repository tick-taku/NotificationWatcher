package com.tick.taku.notificationwatcher.domain.repository.internal

import android.app.Notification
import android.content.Context
import android.service.notification.StatusBarNotification
import com.tick.taku.notificationwatcher.domain.db.NotificationDataBase
import com.tick.taku.notificationwatcher.domain.db.entity.*
import com.tick.taku.notificationwatcher.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import timber.log.Timber

class NotificationRepositoryImpl(private val db: NotificationDataBase): NotificationRepository {

    companion object {

        private val FILTERS = listOf("jp.naver.line.android")

        private const val HEADER_FORMAT = "yyyy/MM/dd (EE)"

    }

    override suspend fun saveNotification(context: Context, sbn: StatusBarNotification) {
        if (FILTERS.contains(sbn.packageName)) {
            Timber.d("Save message to db.")

            saveMessage(context, sbn.notification)

            print(sbn.notification)
        }
    }

    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun roomList(): Flow<List<RoomInfoEntity>> =
        db.roomDao().observeInfo().distinctUntilChanged()

    override suspend fun deleteRoom(id: String) {
        db.roomDao().deleteById(id)
    }

    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun messageList(roomId: String): Flow<Map<String, List<UserMessageEntity>>> =
        db.messageDao().observe(roomId).distinctUntilChanged()
            .map { entity ->
                entity.groupBy { it.message.localTime().toString(HEADER_FORMAT) }
            }

    override suspend fun deleteMessage(id: String) {
        db.messageDao().deleteById(id)
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