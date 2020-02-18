package com.tick.taku.android.corecomponent.util

import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
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

/**
 * Setup on back pressed callback
 */
fun Fragment.setupBackUp(controller: NavController, onBackPressed: () -> Boolean = { true }) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        if (onBackPressed())
            controller.navigateUp()
    }
}