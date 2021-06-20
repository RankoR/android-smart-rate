package com.g2pdev.smartrate

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.repository.RateRepository
import javax.inject.Inject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class RateRepositoryTest : BaseTest() {

    @Inject
    lateinit var rateRepository: RateRepository

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testSessionCount() {
        Assert.assertEquals(0, rateRepository.getSessionCount())

        rateRepository.setSessionCount(2)

        Assert.assertEquals(2, rateRepository.getSessionCount())
    }

    @Test
    fun testIsRated() {
        Assert.assertFalse(rateRepository.isRated())

        rateRepository.setIsRated(true)

        Assert.assertTrue(rateRepository.isRated())
    }

    @Test
    fun testIsNeverAsk() {
        Assert.assertFalse(rateRepository.isNeverAsk())

        rateRepository.setNeverAsk(true)

        Assert.assertTrue(rateRepository.isNeverAsk())
    }

    @Test
    fun testLastPromptSession() {
        Assert.assertEquals(0, rateRepository.getLastPromptSession())

        rateRepository.setLastPromptSession(10)

        Assert.assertEquals(10, rateRepository.getLastPromptSession())
    }
}
