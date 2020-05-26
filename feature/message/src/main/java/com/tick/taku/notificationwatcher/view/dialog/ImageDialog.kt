package com.tick.taku.notificationwatcher.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import coil.api.load

class ImageDialog: DialogFragment() {

    private val args: ImageDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext()).apply {
            val iconView = ImageView(requireContext()).apply {
                load(args.imageUrl)
                setOnClickListener { dismiss() }
            }
            setView(iconView)
        }
            .create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        private const val TAG = "image_dialog"

        fun show(fm: FragmentManager, imageUrl: String?) {
            val dialog = ImageDialog().apply {
                arguments = ImageDialogArgs(imageUrl ?: "").toBundle()
            }
            dialog.show(fm, TAG)
        }
    }

}