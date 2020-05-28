package com.tick.taku.android.corecomponent.ui.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.tick.taku.android.corecomponent.R
import com.tick.taku.android.corecomponent.ktx.getLocale
import java.util.*
import javax.inject.Inject

class SystemViewModel @Inject constructor(private val context: Context,
                                          private val prefs: SharedPreferences): ViewModel() {

    private val _locale: MutableLiveData<Locale> = MutableLiveData()
    val locale: LiveData<Locale> = _locale.distinctUntilChanged()

    private val onLanguageChanged = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
        if (changedKey == context.getString(R.string.pref_key_language)) {
            _locale.postValue(context.getLocale())
        }
    }

    init {
        prefs.registerOnSharedPreferenceChangeListener(onLanguageChanged)
    }

    override fun onCleared() {
        prefs.unregisterOnSharedPreferenceChangeListener(onLanguageChanged)
        super.onCleared()
    }

}