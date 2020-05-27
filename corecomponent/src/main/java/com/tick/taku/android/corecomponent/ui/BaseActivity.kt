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
            setLocale(base?.getLocale())
        }
        super.attachBaseContext(base?.createConfigurationContext(conf))
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        overrideConfiguration?.let {
            val uiMode = it.uiMode
            it.setTo(baseContext.resources.configuration)
            it.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    override fun onResume() {
        super.onResume()
        val currentLocale =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) getCurrentLocale()
            else getCurrentLocaleLegacy()
        if (currentLocale != getLocale()) recreate()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun getCurrentLocale() = resources.configuration.locales.get(0)

    @Suppress("deprecation")
    private fun getCurrentLocaleLegacy() = resources.configuration.locale

    private fun Context.getLocale(): Locale {
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

}