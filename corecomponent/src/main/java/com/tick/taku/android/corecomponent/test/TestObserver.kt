package com.tick.taku.android.corecomponent.test

import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch

class TestObserver<T>(maxCount: Int = DEFAULT_COUNT): Observer<T> {

    companion object {
        private const val DEFAULT_COUNT = 1
    }

    private val countDownLatch = CountDownLatch(maxCount)

    private val _values: MutableList<T> = mutableListOf()
    val values: List<T> = _values

    override fun onChanged(t: T) {
        _values.add(t)
        countDownLatch.countDown()
    }

    fun await() {
        countDownLatch.await()
    }

}