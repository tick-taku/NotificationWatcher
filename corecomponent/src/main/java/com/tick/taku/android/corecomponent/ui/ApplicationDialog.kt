package com.tick.taku.android.corecomponent.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

class ApplicationDialog internal constructor(): DialogFragment() {

    //------------------------------------------------------------------------------------------------------------------
    // MARK: ViewModel
    //------------------------------------------------------------------------------------------------------------------

    internal data class ApplicationDialogModel(
        var priority: Priority = Priority.LOW,
        var isCancelable: Boolean = true,
        var title: String? = null,
        var message: String? = null,
        var positiveButton: Pair<String, DialogInterface.OnClickListener>? = null,
        var negativeButton: Pair<String, DialogInterface.OnClickListener>? = null,
        var customView: View? = null
    ): ViewModel()

    //------------------------------------------------------------------------------------------------------------------
    // MARK: Factory
    //------------------------------------------------------------------------------------------------------------------

    class Factory(private val context: Context) {
        var customView: View? = null

        var priority: Priority = Priority.LOW

        var cancelable: Boolean = true

        var title: String? = null
        fun setTitle(@StringRes resId: Int) { title = context.getString(resId) }

        var message: String? = null
        fun setMessage(@StringRes resId: Int) { message = context.getString(resId) }

        var positiveButton: Pair<String, DialogInterface.OnClickListener>? = null
        fun setPositiveButton(@StringRes resId: Int, listener: () -> Unit = {}) {
            setPositiveButton(context.getString(resId), listener)
        }
        fun setPositiveButton(message: String, listener: () -> Unit = {}) {
            positiveButton = message to DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                listener()
            }
        }

        var negativeButton: Pair<String, DialogInterface.OnClickListener>? = null
        fun setNegativeButton(@StringRes message: Int, listener: () -> Unit = {}) {
            setNegativeButton(context.getString(message), listener)
        }
        fun setNegativeButton(message: String, listener: () -> Unit = {}) {
            negativeButton = message to DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                listener()
            }
        }

        fun create() =
            ApplicationDialog().apply {
                initializer {
                    it.title = title
                    it.message = message
                    it.positiveButton = positiveButton
                    it.negativeButton = negativeButton
                    it.priority = priority
                    it.isCancelable = cancelable
                    it.customView = customView
                }
            }
    }

    //------------------------------------------------------------------------------------------------------------------
    // MARK: Property
    //------------------------------------------------------------------------------------------------------------------

    companion object {

        private const val TAG = "application_dialog"

    }

    enum class Priority {
        HIGH, LOW
    }

    private val viewModel: ApplicationDialogModel by viewModels()

    /**
     * Delay initializing until dialog is attached.
     * Can call only ApplicationDialog.Factory.
     */
    private fun initializer(i: (ApplicationDialogModel) -> Unit) { initializer = i }
    private var initializer: ((ApplicationDialogModel) -> Unit)? = null

    //------------------------------------------------------------------------------------------------------------------
    // MARK: Life cycle
    //------------------------------------------------------------------------------------------------------------------

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initializer?.invoke(viewModel)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext()).apply {
            setTitle(viewModel.title)
            setMessage(viewModel.message)

            viewModel.positiveButton?.let { p ->
                setPositiveButton(p.first, p.second)
            }
            viewModel.negativeButton?.let { n ->
                setNegativeButton(n.first, n.second)
            }

            isCancelable = viewModel.isCancelable

            viewModel.customView?.let { v -> setView(v) }
        }
            .create()

    fun show(fragmentManager: FragmentManager) { show(fragmentManager, TAG) }

    override fun show(manager: FragmentManager, tag: String?) {
        val presented = manager.findFragmentByTag(tag)
        if (presented is DialogFragment && presented.dialog?.isShowing == true) {
            when (viewModel.priority) {
                // Overwrite dialog.
                Priority.HIGH -> presented.dialog?.dismiss()
                // Do not show
                else -> return
            }
        }
        super.show(manager, tag)
    }

}