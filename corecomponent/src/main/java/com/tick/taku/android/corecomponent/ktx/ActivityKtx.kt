package com.tick.taku.android.corecomponent.ktx

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T: ViewModel> AppCompatActivity.viewModelProvider(crossinline viewModels: () -> T): Lazy<T> {
    return viewModels {
        object: ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return viewModels() as T
            }
        }
    }
}