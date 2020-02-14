package com.tick.taku.android.corecomponent.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.parcel.Parcelize

class ApplicationDialog: DialogFragment() {

    //------------------------------------------------------------------------------------------------------------------
    // MARK: Factory
    //------------------------------------------------------------------------------------------------------------------

    @Parcelize
    data class Model(var priority: Priority = Priority.LOW,
                     var isCancelable: Boolean = true,
                     var title: String? = null,
                     var message: String? = null,
                     var positiveButton: Pair<String, DialogInterface.OnClickListener>? = null,
                     var negativeButton: Pair<String, DialogInterface.OnClickListener>? = null): Parcelable

    class Factory(private val context: Context) {
        private val model = Model()

        var customView: View? = null

        var priority: Priority
            get() = model.priority
            set(value) { model.priority = value }

        var isCancelable: Boolean
            get() = model.isCancelable
            set(value) { model.isCancelable = value }

        var title: String?
            get() = model.title
            set(value) { model.title = value }
        fun setTitle(@StringRes resId: Int) { model.title = context.getString(resId) }

        var message: String?
            get() = model.message
            set(value) { model.message = value }
        fun setMessage(@StringRes resId: Int) { model.message = context.getString(resId) }

        fun setPositiveButton(@StringRes resId: Int, listener: () -> Unit = {}) {
            setPositiveButton(context.getString(resId), listener)
        }
        fun setPositiveButton(message: String, listener: () -> Unit = {}) {
            model.positiveButton = message to DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                listener()
            }
        }

        fun setNegativeButton(@StringRes message: Int, listener: () -> Unit = {}) {
            setNegativeButton(context.getString(message), listener)
        }
        fun setNegativeButton(message: String, listener: () -> Unit = {}) {
            model.negativeButton = message to DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                listener()
            }
        }

        /**
         * Create ApplicationDialog
         */
        fun create() =
            ApplicationDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(MODEL_KEY, model)
                }
                cv = customView
            }
    }

    //------------------------------------------------------------------------------------------------------------------
    // MARK: Property
    //------------------------------------------------------------------------------------------------------------------

    companion object {

        private const val TAG = "application_dialog"

        private const val MODEL_KEY = "application_dialog_model"

    }

    enum class Priority {
        HIGH, LOW
    }

    private var priority: Priority = Priority.LOW

    // TODO: passing custom view into fragment
    private var cv: View? = null

    //------------------------------------------------------------------------------------------------------------------
    // MARK: Life cycle
    //------------------------------------------------------------------------------------------------------------------

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext()).apply {
            arguments?.getParcelable<Model>(MODEL_KEY)?.let {
                setTitle(it.title)
                setMessage(it.message)

                it.positiveButton?.let { p ->
                    setPositiveButton(p.first, p.second)
                }
                it.negativeButton?.let { n ->
                    setNegativeButton(n.first, n.second)
                }

                isCancelable = it.isCancelable

                priority = it.priority

                cv?.let { v -> setView(v) }
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