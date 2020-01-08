package com.g2pdev.smartrate.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.interactor.never_ask.IsNeverAsk
import com.g2pdev.smartrate.interactor.never_ask.SetNeverAsk
import javax.inject.Inject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class NeverAskInteractorTest : BaseTest() {

    @Inject
    lateinit var isNeverAsk: IsNeverAsk

    @Inject
    lateinit var setNeverAsk: SetNeverAsk

    @Before
    fun setUp() {
        createDaggerComponent()
            .inject(this)
    }

    @Test
    fun testIsNeverAsk() {
        isNeverAsk
            .exec()
            .test()
            .assertValue(false)

        setNeverAsk
            .exec(true)
            .test()
            .assertComplete()

        isNeverAsk
            .exec()
            .test()
            .assertValue(true)
    }
}
