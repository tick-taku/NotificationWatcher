package com.tick.taku.notificationwatcher.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.tick.taku.android.corecomponent.di.Injectable
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

object AppInjector {

    fun initialize(app: Application) {
        app.registerActivityLifecycleCallbacks { activity, _ ->
            handleActivity(activity)
        }
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallback(true) { _, f, _ ->
                if (f is Injectable) AndroidSupportInjection.inject(f)
            }
        }
    }

}

private inline fun Application.registerActivityLifecycleCallbacks(crossinline onCreated: (Activity, Bundle?) -> Unit) {
    registerActivityLifecycleCallbacks(object: Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) { onCreated(activity, savedInstanceState) }
        override fun onActivityDestroyed(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {}
    })
}

private inline fun FragmentManager.registerFragmentLifecycleCallback(
    recursive: Boolean,
    crossinline onAttached: (FragmentManager, Fragment, Context) -> Unit
) {
    registerFragmentLifecycleCallbacks(object: FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
            onAttached(fm, f, context)
        }
    }, recursive)
}
