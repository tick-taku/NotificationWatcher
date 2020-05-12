package com.tick.taku.android.corecomponent.ktx

import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.navigation.NavigationView

@MainThread
inline fun <reified DB: ViewDataBinding> NavigationView.getBinding(): DB = DataBindingUtil.bind(getHeaderView(0))!!