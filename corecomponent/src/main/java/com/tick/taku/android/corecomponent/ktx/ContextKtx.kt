package com.tick.taku.android.corecomponent.ktx

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, text, duration).apply { show() }
}

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, resId, duration).apply { show() }
}

/**
 * Show activity
 *
 * @param extras Extras setting
 */
inline fun <reified T: AppCompatActivity> Context.openActivity(crossinline extras: Intent.() -> Unit = {}) {
    val intent = activity<T>().apply {
        extras()
    }
    startActivity(intent)
}

/**
 * Create activity intent.
 */
inline fun <reified T: AppCompatActivity> Context.activity() = Intent(this, T::class.java)