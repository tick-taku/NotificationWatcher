package com.tick.taku.notificationwatcher.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import kotlinx.android.parcel.Parcelize

class ApplicationDialog private constructor(): DialogFragment() {

    //------------------------------------------------------------------------------------------------------------------
    // MARK: Factory
    //------------------------------------------------------------------------------------------------------------------

    @Parcelize
    data class Model(var priority: Priority = Priority.LOW,
                     var isCancelable: Boolean = true,
                     var title: String? = null,
                     var message: String? = null,
                     var positiveButton: Pair<String, () -> Unit>? = null,
                     var negativeButton: Pair<String, () -> Unit>? = null): Parcelable

    class Factory(private val context: Context) {
        private val model = Model()

        fun setPriority(p: Priority) { model.priority = p }

        fun setCancelable(c: Boolean) { model.isCancelable = c }

        fun setTitle(t: String) { model.title = t }
        fun setTitle(@StringRes resId: Int) { model.title = context.getString(resId) }

        fun setMessage(m: String) { model.message = m }
        fun setMessage(@StringRes resId: Int) { model.message = context.getString(resId) }

        fun setPositiveButton(@StringRes resId: Int, listener: () -> Unit = {}) {
            setPositiveButton(context.getString(resId), listener)
        }
        fun setPositiveButton(message: String, listener: () -> Unit = {}) {
            model.positiveButton = message to listener
        }

        fun setNegativeButton(@StringRes message: Int, listener: () -> Unit = {}) {
            setNegativeButton(context.getString(message), listener)
        }
        fun setNegativeButton(message: String, listener: () -> Unit = {}) {
            model.negativeButton = message to listener
        }

        /**
         * Create ApplicationDialog
         */
        fun create() =
            ApplicationDialog().apply {
                val args = ApplicationDialogArgs.Builder(model).build()
                arguments = args.toBundle()
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

    private val args: ApplicationDialogArgs by navArgs()

    private var priority: Priority = Priority.LOW

    //------------------------------------------------------------------------------------------------------------------
    // MARK: Life cycle
    //------------------------------------------------------------------------------------------------------------------

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext()).apply {
            args.model.let {
                setTitle(it.title)
                setMessage(it.message)

                it.positiveButton?.let { p ->
                    setPositiveButton(p.first) { _, _ ->
                        p.second()
                        dismiss()
                    }
                }

                it.negativeButton?.let { n ->
                    setNegativeButton(n.first) {_, _ ->
                        n.second()
                        dismiss()
                    }
                }

                isCancelable = it.isCancelable

                priority = it.priority
            }
        }
            .create()

    fun show(fragmentManager: FragmentManager) { show(fragmentManager, TAG) }

    override fun show(manager: FragmentManager, tag: String?) {
        val presented = manager.findFragmentByTag(tag)
        if (presented is DialogFragment && presented.dialog?.isShowing == true) {
            when (priority) {
                // Overwrite dialog.
                Priority.HIGH -> presented.dialog?.dismiss()
                // Do not show
                else -> return
            }
        }
        super.show(manager, tag)
    }

}