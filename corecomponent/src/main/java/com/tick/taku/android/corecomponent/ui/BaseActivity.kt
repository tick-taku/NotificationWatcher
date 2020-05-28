package com.tick.taku.android.corecomponent.ui

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.tick.taku.android.corecomponent.ktx.getLocale
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

    protected fun isLocaleChanged(locale: Locale): Boolean {
        val currentLocale =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) getCurrentLocale()
            else getCurrentLocaleLegacy()
        return currentLocale != locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun getCurrentLocale() = resources.configuration.locales.get(0)

    @Suppress("deprecation")
    private fun getCurrentLocaleLegacy() = resources.configuration.locale

}