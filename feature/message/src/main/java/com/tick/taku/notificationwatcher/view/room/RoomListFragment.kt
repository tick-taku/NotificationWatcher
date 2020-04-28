package com.tick.taku.notificationwatcher.view.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.tick.taku.android.corecomponent.di.Injectable
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.android.corecomponent.util.showDialog
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity
import com.tick.taku.notificationwatcher.view.R
import com.tick.taku.notificationwatcher.view.room.item.RoomItem
import com.tick.taku.notificationwatcher.view.room.viewmodel.RoomListViewModel
import com.tick.taku.notificationwatcher.view.databinding.FragmentRoomListBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.GroupieViewHolder
import javax.inject.Inject
import javax.inject.Provider

class RoomListFragment: Fragment(R.layout.fragment_room_list), Injectable {

    private val binding: FragmentRoomListBinding by dataBinding()

    @Inject lateinit var viewModelFactory: Provider<RoomListViewModel>
    private val viewModel: RoomListViewModel by viewModelProvider {
        viewModelFactory.get()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRoomList()
    }

    private fun setupRoomList() {
        val roomListAdapter = GroupAdapter<GroupieViewHolder<*>>()

        binding.roomList.adapter = roomListAdapter

        viewModel.roomList.observe(viewLifecycleOwner) {
            val items = it.map { entity ->
                RoomItem(entity).apply {
                    setOnClickListener { e -> showMessage(e) }
                    setOnLongClickListener { e -> showConfirmationDialog(e.id) }
                }
            }
            roomListAdapter.update(items)
        }
    }

    private fun showMessage(entity: RoomInfoEntity) {
        val destination = RoomListFragmentDirections.showMessages(
            title = entity.name,
            roomId = entity.id
        )
        findNavController().navigate(destination)
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