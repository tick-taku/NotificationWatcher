package com.tick.taku.notificationwatcher.view.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.android.corecomponent.util.showDialog
import com.tick.taku.notificationwatcher.MyApplication
import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.FragmentRoomListBinding
import com.tick.taku.notificationwatcher.domain.repository.internal.NotificationRepositoryImpl
import com.tick.taku.notificationwatcher.view.room.item.RoomItem
import com.tick.taku.notificationwatcher.view.room.viewmodel.RoomListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.GroupieViewHolder

class RoomListFragment: Fragment(R.layout.fragment_room_list) {

    private val binding: FragmentRoomListBinding by dataBinding()

    // TODO: DI
    private val viewModel: RoomListViewModel by viewModelProvider {
        RoomListViewModel(NotificationRepositoryImpl((requireActivity().application as MyApplication).db))
    }

    private val roomListAdapter: GroupAdapter<GroupieViewHolder<*>> by lazy {
        GroupAdapter<GroupieViewHolder<*>>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRoomList()
    }

    private fun setupRoomList() {
        binding.roomList.adapter = roomListAdapter

        viewModel.roomList.observe(viewLifecycleOwner) {
            val items = it.map { entity ->
                RoomItem(entity).apply {
                    setOnLongClickListener { e -> showConfirmationDialog(e.id) }
                }
            }
            roomListAdapter.update(items)
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