package com.tick.taku.android.corecomponent.util

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.tick.taku.android.corecomponent.ui.ApplicationDialog

/**
 * Show dialog fragment
 *
 * @param builder Set dialog parameters
 */
inline fun Fragment.showDialog(builder: ApplicationDialog.Factory.() -> Unit): DialogFragment? =
    if (isDetached) null
    else ApplicationDialog.Factory(requireContext())
        .apply { builder(this) }
        .create()
        .also { it.show(childFragmentManager) }