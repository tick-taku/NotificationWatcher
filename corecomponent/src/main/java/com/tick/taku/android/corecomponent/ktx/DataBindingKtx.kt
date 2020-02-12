package com.tick.taku.android.corecomponent.ktx

import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@MainThread
inline fun <reified DB: ViewDataBinding> Fragment.dataBinding() =
    object: ReadOnlyProperty<Fragment, DB> {
        private var binding: DB? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): DB =
            binding ?: DataBindingUtil.bind<DB>(view!!)!!.also {
                it.lifecycleOwner = thisRef.viewLifecycleOwner
                binding = it
                thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                    @Suppress("unused")
                    fun onDestroyView() {
                        thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
                        binding = null
                    }
                })
            }
    }

@MainThread
inline fun <reified DB: ViewDataBinding> AppCompatActivity.dataBinding() =
    DataBindingLazy<DB> { window.findViewById<ViewGroup>(android.R.id.content)[0] }

class DataBindingLazy<DB: ViewDataBinding>(private val view: () -> View): Lazy<DB> {
    private var cached: DB? = null
    override val value: DB
        get() = cached ?: run {
            DataBindingUtil.bind<DB>(view())!!.also {
                it.executePendingBindings()
                cached = it
            }
        }
    override fun isInitialized(): Boolean = cached != null
}