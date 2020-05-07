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
        Configuration.Builder().setWorkerFactory { appContext, className, workerParameters ->
            when (Class.forName(className)) {
                AutoDeletionWorker::class.java -> assistedFactory.create(appContext, workerParameters)
                else -> null
            }
        }
            .let { WorkManager.initialize(context, it.build()) }

        return WorkManager.getInstance(context)
    }

}

private fun Configuration.Builder.setWorkerFactory(f: (Context, String, WorkerParameters) -> ListenableWorker?): Configuration.Builder {
    return setWorkerFactory(object: WorkerFactory() {
        override fun createWorker(appContext: Context,
                                  workerClassName: String,
                                  workerParameters: WorkerParameters): ListenableWorker? {
            return f(appContext, workerClassName, workerParameters)
        }
    })
}