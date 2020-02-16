package com.tick.taku.notificationwatcher.view.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.android.corecomponent.util.setupBackUp
import com.tick.taku.android.corecomponent.util.showDialog
import com.tick.taku.notificationwatcher.MyApplication
import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.FragmentMessageBinding
import com.tick.taku.notificationwatcher.domain.repository.internal.NotificationRepositoryImpl
import com.tick.taku.notificationwatcher.view.message.item.MessageItem
import com.tick.taku.notificationwatcher.view.message.viewmodel.MessageViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.GroupieViewHolder

class MessageFragment: Fragment(R.layout.fragment_message) {

    private val binding: FragmentMessageBinding by dataBinding()

    // TODO: DI
    private val viewModel: MessageViewModel by viewModelProvider {
        MessageViewModel(
            NotificationRepositoryImpl((requireActivity().application as MyApplication).db),
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

        viewModel.messageList.observe(viewLifecycleOwner) {
            val items = it.map { entity ->
                MessageItem(entity).apply {
                    setOnLongClickListener { e -> showConfirmationDialog(e.id) }
                }
            }
            messageListAdapter.update(items)
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