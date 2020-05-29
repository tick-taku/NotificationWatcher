package com.tick.taku.android.corecomponent.ktx

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.core.content.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.tick.taku.android.corecomponent.ui.ApplicationDialog

inline fun <reified T: ViewModel> Fragment.viewModelProvider(crossinline viewModels: () -> T): Lazy<T> {
    return viewModels {
        object: ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return viewModels() as T
            }
        }
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModelProvider(crossinline body: () -> T): Lazy<T> {
    return activityViewModels {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return body() as T
            }
        }
    }
}

fun Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast {
    return requireContext().toast(text, duration)
}

fun Fragment.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    return requireContext().toast(resId, duration)
}

fun Fragment.hideKeyboard(v: View) {
    context?.getSystemService<InputMethodManager>()
        ?.hideSoftInputFromWindow(v.windowToken, 0)
}

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