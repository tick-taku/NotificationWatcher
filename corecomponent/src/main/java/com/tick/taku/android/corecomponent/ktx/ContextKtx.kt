package com.tick.taku.android.corecomponent.ktx

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.tick.taku.android.corecomponent.R
import com.tick.taku.android.corecomponent.di.DirectlyModuleProvider
import java.util.*

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

// ---- internal ----

internal fun Context.getLocale(): Locale {
    val pref = (applicationContext as? DirectlyModuleProvider)?.providerSharedPrefs() guard {
        return Locale.getDefault()
    }

    val (key, value) = run {
        getString(R.string.pref_key_language) to getString(R.string.localized_language_value_system)
    }
    return when (pref.getString(key, value)) {
        getString(R.string.localized_language_value_en) -> Locale.ENGLISH
        getString(R.string.localized_language_value_ja) -> Locale.JAPANESE
        else -> Locale.getDefault()
    }
}