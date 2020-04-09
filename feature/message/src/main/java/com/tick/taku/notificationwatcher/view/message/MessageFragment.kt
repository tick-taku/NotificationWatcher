package com.tick.taku.notificationwatcher.view.message

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.toast
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.android.corecomponent.util.setupBackUp
import com.tick.taku.android.corecomponent.util.showDialog
import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.domain.repository.internal.NotificationRepositoryImpl
import com.tick.taku.notificationwatcher.view.databinding.FragmentMessageBinding
import com.tick.taku.notificationwatcher.view.message.item.MessageHeaderItem
import com.tick.taku.notificationwatcher.view.message.item.MessageItem
import com.tick.taku.notificationwatcher.view.message.viewmodel.MessageViewModel
import com.tick.taku.notificationwatcher.view.DatabaseTmp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.databinding.GroupieViewHolder

class MessageFragment: Fragment(R.layout.fragment_message) {

    private val binding: FragmentMessageBinding by dataBinding()

    // TODO: DI
    private val viewModel: MessageViewModel by viewModelProvider {
        MessageViewModel(
            NotificationRepositoryImpl(DatabaseTmp.db!!),
            args.roomId
        )
    }

    private val args: MessageFragmentArgs by navArgs()

    private val messageListAdapter: GroupAdapter<GroupieViewHolder<*>> by lazy {
        GroupAdapter<GroupieViewHolder<*>>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupBackUp(findNavController())

        setupMessageList()
    }

    private fun setupMessageList() {
        binding.messageList.adapter = messageListAdapter

        viewModel.messageList.observe(viewLifecycleOwner) { list ->
            val isShowName = list.all { items -> items.value.all { it.user.name != args.title } }
            val items = list.mapKeys { MessageHeaderItem(it.key) }
                .mapValues {
                    it.value.map { entity ->
                        MessageItem(entity, isShowName).apply {
                            setOnMessageClickListener { m -> copyToClipboard(m) }
                            setOnLongClickListener { e -> showConfirmationDialog(e.id) }
                        }
                    }
                }.toSections()
            messageListAdapter.update(items)
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

}