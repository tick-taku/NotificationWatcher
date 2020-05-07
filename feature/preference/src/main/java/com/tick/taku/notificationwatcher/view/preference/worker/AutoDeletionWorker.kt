package com.tick.taku.notificationwatcher.view.preference.worker

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.tick.taku.notificationwatcher.domain.db.NotificationDatabase
import com.tick.taku.notificationwatcher.view.preference.R

class AutoDeletionWorker @AssistedInject constructor(@Assisted private val context: Context,
                                                     @Assisted params: WorkerParameters,
                                                     private val db: NotificationDatabase): CoroutineWorker(context, params) {

    companion object {
        const val TAG = "auto_deletion_worker"
    }

    override suspend fun doWork(): Result {
        val before = context.let {
            PreferenceManager.getDefaultSharedPreferences(it).getInt(it.getString(R.string.pref_key_delete_from), 1)
        }
        db.deleteMessageBefore(before)

        return Result.success()
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(context: Context, params: WorkerParameters): AutoDeletionWorker
    }

}