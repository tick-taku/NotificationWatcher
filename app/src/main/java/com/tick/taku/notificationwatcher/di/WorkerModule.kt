package com.tick.taku.notificationwatcher.di

import android.content.Context
import androidx.work.*
import com.tick.taku.notificationwatcher.view.preference.di.WorkerAssistedInjectModule
import com.tick.taku.notificationwatcher.view.preference.worker.AutoDeletionWorker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [WorkerAssistedInjectModule::class])
object WorkerModule {

    @Singleton @Provides
    fun provideWorkManager(context: Context, assistedFactory: AutoDeletionWorker.Factory): WorkManager {
        val factory = object: WorkerFactory() {
            override fun createWorker(appContext: Context,
                                      workerClassName: String,
                                      workerParameters: WorkerParameters): ListenableWorker? {
                return when (Class.forName(workerClassName)) {
                    AutoDeletionWorker::class.java -> assistedFactory.create(appContext, workerParameters)
                    else -> null
                }
            }
        }
        Configuration.Builder().setWorkerFactory(factory)
            .let { WorkManager.initialize(context, it.build()) }

        return WorkManager.getInstance(context)
    }

}