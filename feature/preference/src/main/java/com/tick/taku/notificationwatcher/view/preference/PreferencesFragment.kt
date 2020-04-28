package com.tick.taku.notificationwatcher.view.preference

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.preference.*
import com.tick.taku.notificationwatcher.view.preference.viewmodel.PreferencesViewModel

class PreferencesFragment: PreferenceFragmentCompat() {

    private val viewModel: PreferencesViewModel by viewModels()

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