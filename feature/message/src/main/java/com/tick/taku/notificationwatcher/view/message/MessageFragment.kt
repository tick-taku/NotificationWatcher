package com.tick.taku.notificationwatcher.view.message

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tick.taku.android.corecomponent.di.Injectable
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.hideKeyboard
import com.tick.taku.android.corecomponent.ktx.toast
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.android.corecomponent.util.setupBackUp
import com.tick.taku.android.corecomponent.util.showDialog
import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.view.databinding.FragmentMessageBinding
import com.tick.taku.notificationwatcher.view.dialog.ImageDialog
import com.tick.taku.notificationwatcher.view.message.item.MessageHeaderItem
import com.tick.taku.notificationwatcher.view.message.item.MessageItem
import com.tick.taku.notificationwatcher.view.message.viewmodel.MessageViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.databinding.GroupieViewHolder
import java.net.URLEncoder
import javax.inject.Inject

class MessageFragment: Fragment(R.layout.fragment_message), Injectable {

    private val binding: FragmentMessageBinding by dataBinding()

    private val args: MessageFragmentArgs by navArgs()

    @Inject lateinit var viewModelFactory: MessageViewModel.Factory
    private val viewModel: MessageViewModel by viewModelProvider {
        viewModelFactory.create(args.roomId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupBackUp(findNavController())

        setupMessageList()

        setupPostMessageField()
    }

    private fun setupMessageList() {
        val messageListAdapter = GroupAdapter<GroupieViewHolder<*>>()

        binding.messageList.adapter = messageListAdapter

        viewModel.messageList.observe(viewLifecycleOwner) { list ->
            val items = list.mapKeys { MessageHeaderItem(it.key) }
                .mapValues {
                    it.value.map { entity ->
                        MessageItem(entity, viewModel, viewLifecycleOwner).apply {
                            setOnMessageClickListener { m -> copyToClipboard(m) }
                            setOnImageClickListener { u -> ImageDialog.show(childFragmentManager, u) }
                            setOnLongClickListener { e -> showConfirmationDialog(e.id) }
                        }
                    }
                }.toSections().asReversed()
            messageListAdapter.update(items)

            binding.messageList.scrollToPosition(messageListAdapter.itemCount - 1)
        }
    }

    private fun Map<MessageHeaderItem, List<MessageItem>>.toSections() = map {
        Section().apply {
            setHeader(it.key)
            addAll(it.value)
        }
    }

    private fun copyToClipboard(message: String) {
        requireContext().getSystemService<ClipboardManager>()?.let {
            val clipData = ClipData.newPlainText(getString(R.string.clipboard_label_message), message)
            it.setPrimaryClip(clipData)

            toast(R.string.copy_to_clipboard)
        }
    }

    private fun showConfirmationDialog(id: String) {
        showDialog {
            setMessage(R.string.msg_confirmation_delete_room)
            setPositiveButton(R.string.ok) {
                viewModel.delete(id)
            }
            setNegativeButton(R.string.cancel)
        }
    }

    private fun setupPostMessageField() {
        binding.viewModel = viewModel
        binding.messageInputLayout.setEndIconOnClickListener {
            postMessageToLine()
        }

        @Suppress("ClickableViewAccessibility")
        binding.messageList.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) binding.messageList.requestFocus()
            false
        }
        binding.messageInputField.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v)
        }
    }

    private fun postMessageToLine() {
        viewModel.outgoingMessage.value?.takeIf { it.isNotEmpty() }?.let {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://line.me/R/msg/text/${URLEncoder.encode(it, "utf-8")}")
            }
            startActivity(intent)
        }
    }

}