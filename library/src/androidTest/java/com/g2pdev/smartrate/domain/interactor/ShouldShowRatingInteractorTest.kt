package com.g2pdev.smartrate.domain.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.data.preference.SessionCountPreference
import com.g2pdev.smartrate.domain.interactor.is_rated.SetIsRated
import com.g2pdev.smartrate.domain.interactor.last_prompt.SetLastPromptSessionToCurrent
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
internal class ShouldShowRatingInteractorTest : BaseTest() {

    @Inject
    lateinit var sessionCountPreference: SessionCountPreference

    @Inject
    lateinit var setIsRated: SetIsRated

    @Inject
    lateinit var setNeverAsk: SetNeverAsk

    @Inject
    lateinit var setLastPromptSessionToCurrent: SetLastPromptSessionToCurrent

    @Inject
    lateinit var shouldShowRating: ShouldShowRating

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testSessionCountNotReached() {
        runBlocking {
            val result = shouldShowRating.exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )

            Assert.assertFalse(result)
        }
    }

    @Test
    fun testSessionCountReached() {
        sessionCountPreference.put(10)

        runBlocking {
            val result = shouldShowRating.exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )

            Assert.assertTrue(result)
        }
    }

    @Test
    fun testMinSessionCountBetweenPromptsNotReached() {
        sessionCountPreference.put(10)

        runBlocking {
            setLastPromptSessionToCurrent.exec()

            val result = shouldShowRating.exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )

            Assert.assertFalse(result)
        }
    }

    @Test
    fun testMinSessionCountBetweenPromptsReached() {
        sessionCountPreference.put(10)

        runBlocking {
            setLastPromptSessionToCurrent.exec()

            sessionCountPreference.put(13)

            val result = shouldShowRating.exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )

            Assert.assertTrue(result)
        }
    }

    @Test
    fun testNeverAsk() {
        sessionCountPreference.put(100)

        runBlocking {
            setNeverAsk.exec(true)

            val result = shouldShowRating.exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )

            Assert.assertFalse(result)
        }
    }

    @Test
    fun testIsRated() {
        sessionCountPreference.put(100)

        runBlocking {
            setIsRated.exec(true)

            val result = shouldShowRating.exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )

            Assert.assertFalse(result)
        }
    }
}
