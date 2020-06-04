package com.tick.taku.android.corecomponent.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class ViewModelTestRule: TestRule {

    private val delegate = RuleChain
        .outerRule(InstantTaskExecutorRule())
        .around(CoroutineTestRule())

    override fun apply(base: Statement?, description: Description?): Statement =
        delegate.apply(base, description)

}