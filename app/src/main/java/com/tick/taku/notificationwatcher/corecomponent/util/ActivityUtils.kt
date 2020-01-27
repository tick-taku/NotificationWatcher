package com.tick.taku.notificationwatcher.corecomponent.util

import androidx.appcompat.app.AppCompatActivity
import com.tick.taku.notificationwatcher.view.dialog.ApplicationDialog

/**
 * Show common dialog.
 */
fun AppCompatActivity.showDialog(factory: ApplicationDialog.Factory.() -> Unit) {
    if (isDestroyed) return

    val dialog = ApplicationDialog.Factory(applicationContext).apply {
        factory(this)
    }.create()
    dialog.show(supportFragmentManager)
}