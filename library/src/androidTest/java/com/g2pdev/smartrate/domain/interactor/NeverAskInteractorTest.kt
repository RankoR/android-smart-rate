package com.g2pdev.smartrate.domain.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.domain.interactor.never_ask.IsNeverAsk
import com.g2pdev.smartrate.domain.interactor.never_ask.SetNeverAsk
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class NeverAskInteractorTest : BaseTest() {

    @Inject
    lateinit var isNeverAsk: IsNeverAsk

    @Inject
    lateinit var setNeverAsk: SetNeverAsk

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testIsNeverAsk() {
        Assert.assertFalse(isNeverAsk.exec())

        runBlocking {
            setNeverAsk.exec(true)
        }

        Assert.assertTrue(isNeverAsk.exec())
    }
}
