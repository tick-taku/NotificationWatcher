package com.tick.taku.android.corecomponent.ktx

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tick.taku.android.corecomponent.ui.ApplicationDialog

inline fun <reified T: ViewModel> AppCompatActivity.viewModelProvider(crossinline viewModels: () -> T): Lazy<T> {
    return viewModels {
        object: ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return viewModels() as T
            }
        }
    }
}

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