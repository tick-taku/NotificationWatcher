package com.tick.taku.android.corecomponent.util

import androidx.appcompat.app.AppCompatActivity
import com.tick.taku.android.corecomponent.ui.ApplicationDialog

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