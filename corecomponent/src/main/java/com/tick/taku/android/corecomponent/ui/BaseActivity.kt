package com.tick.taku.android.corecomponent.ui

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.tick.taku.android.corecomponent.R
import com.tick.taku.android.corecomponent.di.DirectlyModuleProvider
import com.tick.taku.android.corecomponent.ktx.guard
import java.util.*

abstract class BaseActivity(@LayoutRes layoutId: Int): AppCompatActivity(layoutId) {

    override fun attachBaseContext(base: Context?) {
        val conf = Configuration(base?.resources?.configuration).apply {
            setLocale(getLocale(base))
        }
        super.attachBaseContext(base?.createConfigurationContext(conf))
    }

    override fun onResume() {
        super.onResume()

        val currentLocale =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) getCurrentLocale()
            else getCurrentLocaleLegacy()
        if (currentLocale != getLocale(this)) {
            recreate()
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun getCurrentLocale() = resources.configuration.locales.get(0)

    @Suppress("deprecation")
    private fun getCurrentLocaleLegacy() = resources.configuration.locale

    private fun getLocale(context: Context?): Locale {
        val pref = (context?.applicationContext as? DirectlyModuleProvider)?.providerSharedPrefs() guard {
            return Locale.getDefault()
        }

        val (key, value) = context!!.let {
            it.getString(R.string.pref_key_language) to it.getString(R.string.localized_language_value_system)
        }
        return when (pref.getString(key, value)) {
            context.getString(R.string.localized_language_value_en) -> Locale.ENGLISH
            context.getString(R.string.localized_language_value_ja) -> Locale.JAPANESE
            else -> Locale.getDefault()
        }
    }

}