package com.tick.taku.notificationwatcher.view.message

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.notificationwatcher.MyApplication
import com.tick.taku.notificationwatcher.R
import com.tick.taku.notificationwatcher.databinding.FragmentMessageBinding
import com.tick.taku.notificationwatcher.domain.repository.internal.NotificationRepositoryImpl
import com.tick.taku.notificationwatcher.view.message.viewmodel.MessageViewModel

class MessageFragment: Fragment(R.layout.fragment_message) {

    private val binding: FragmentMessageBinding by dataBinding()

    private val viewModel: MessageViewModel by viewModelProvider {
        MessageViewModel(NotificationRepositoryImpl((requireActivity().application as MyApplication).db))
    }

    private val args: MessageFragmentArgs by navArgs()

}