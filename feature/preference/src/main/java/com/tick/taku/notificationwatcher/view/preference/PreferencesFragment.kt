package com.tick.taku.notificationwatcher.view.preference

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.preference.*
import com.tick.taku.android.corecomponent.di.Injectable
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.notificationwatcher.view.preference.viewmodel.PreferencesViewModel
import javax.inject.Inject
import javax.inject.Provider

class PreferencesFragment: PreferenceFragmentCompat(), Injectable {

    @Inject lateinit var viewModelFactory: Provider<PreferencesViewModel>
    private val viewModel: PreferencesViewModel by viewModelProvider {
        viewModelFactory.get()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferenceManager?.findPreference<SwitchPreferenceCompat>(getString(R.string.pref_key_enable_auto_delete))?.let {
            viewModel.setIsEnabledAutoDelete(it.isChecked)
            it.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                viewModel.setIsEnabledAutoDelete(newValue as Boolean)
                return@OnPreferenceChangeListener true
            }
        }

        viewModel.isEnabledAutoDelete.observe(viewLifecycleOwner) { isEnabled ->
            preferenceManager?.findPreference<SeekBarPreference>(getString(R.string.pref_key_delete_from))?.let {
                it.isEnabled = isEnabled
            }
        }
    }

}