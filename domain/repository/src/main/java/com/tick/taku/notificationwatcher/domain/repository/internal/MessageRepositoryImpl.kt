package com.tick.taku.notificationwatcher.domain.repository.internal

import android.app.Notification
import android.content.Context
import android.content.SharedPreferences
import android.service.notification.StatusBarNotification
import com.tick.taku.android.corecomponent.R
import com.tick.taku.notificationwatcher.domain.db.MessageDatabase
import com.tick.taku.notificationwatcher.domain.db.entity.*
import com.tick.taku.notificationwatcher.domain.repository.MessageRepository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
internal class MessageRepositoryImpl @Inject constructor(private val context: Context,
                                                         private val db: MessageDatabase,
                                                         private val prefs: SharedPreferences) : MessageRepository {

    companion object {

        private val FILTERS = listOf("jp.naver.line.android")

        private const val HEADER_FORMAT = "yyyy/MM/dd (EE)"

    }

    private val isEnabledUrlPreview: ConflatedBroadcastChannel<Boolean> = ConflatedBroadcastChannel(false)

    init {
        prefs.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == context.getString(R.string.pref_key_enable_auto_delete))
                postIsEnabledUrlPreview()
        }
    }

    override suspend fun saveNotification(sbn: StatusBarNotification) {
        if (FILTERS.contains(sbn.packageName)) {
            Timber.d("Save message to db.")

            saveMessage(context, sbn.notification)

            print(sbn.notification)
        }
    }

    override fun roomList(): Flow<List<RoomInfoEntity>> =
        db.observeRooms().distinctUntilChanged()

    override suspend fun deleteRoom(id: String) {
        db.deleteRoom(id)
    }

    override fun messageList(roomId: String): Flow<Map<String, List<UserMessageEntity>>> =
        db.observeMessages(roomId).distinctUntilChanged()
            .map { entity ->
                entity.groupBy { it.message.localTime().toString(HEADER_FORMAT) }
            }

    override suspend fun deleteMessage(id: String) {
        db.deleteMessage(id)
    }

    @UseExperimental(kotlinx.coroutines.FlowPreview::class)
    override fun isShowUrlPreview(): Flow<Boolean> =
        isEnabledUrlPreview.asFlow()
            .onStart { postIsEnabledUrlPreview() }

    private fun postIsEnabledUrlPreview() {
        val (key, default) = context.run {
            getString(R.string.pref_key_enable_url_preview) to resources.getBoolean(R.bool.is_show_url_preview)
        }
        isEnabledUrlPreview.offer(prefs.getBoolean(key, default))
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