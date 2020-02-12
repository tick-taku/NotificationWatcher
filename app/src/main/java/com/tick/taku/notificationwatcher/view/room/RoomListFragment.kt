package com.tick.taku.notificationwatcher.view.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.FragmentRoomListBinding
import com.tick.taku.notificationwatcher.domain.db.NotificationDataBase
import com.tick.taku.notificationwatcher.domain.repository.internal.NotificationRepositoryImpl
import com.tick.taku.notificationwatcher.view.room.viewmodel.RoomListViewModel

class RoomListFragment: Fragment(R.layout.fragment_room_list) {

    private val binding: FragmentRoomListBinding by dataBinding()

    private val viewModel: RoomListViewModel by viewModelProvider {
        RoomListViewModel(NotificationRepositoryImpl(NotificationDataBase.getInstance(requireContext())))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}