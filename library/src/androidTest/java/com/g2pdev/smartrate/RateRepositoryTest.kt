package com.g2pdev.smartrate

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.repository.RateRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
internal class RateRepositoryTest : BaseTest() {

    @Inject
    lateinit var rateRepository: RateRepository

    @Before
    fun setUp() {
        createDaggerComponent()
            .inject(this)
    }

    @Test
    fun testSessionCount() {
        rateRepository
            .getSessionCount()
            .test()
            .assertValue(0)

        rateRepository
            .setSessionCount(2)
            .test()
            .assertComplete()

        rateRepository
            .getSessionCount()
            .test()
            .assertValue(2)
    }

    @Test
    fun testIsRated() {
        rateRepository
            .isRated()
            .test()
            .assertValue(false)

        rateRepository
            .setIsRated(true)
            .test()
            .assertComplete()

        rateRepository
            .isRated()
            .test()
            .assertValue(true)
    }

    @Test
    fun testIsNeverAsk() {
        rateRepository
            .isNeverAsk()
            .test()
            .assertValue(false)

        rateRepository
            .setNeverAsk(true)
            .test()
            .assertComplete()

        rateRepository
            .isNeverAsk()
            .test()
            .assertValue(true)
    }

    @Test
    fun testLastPromptSession() {
        rateRepository
            .getLastPromptSession()
            .test()
            .assertValue(0)

        rateRepository
            .setLastPromptSession(10)
            .test()
            .assertComplete()

        rateRepository
            .getLastPromptSession()
            .test()
            .assertValue(10)
    }

}