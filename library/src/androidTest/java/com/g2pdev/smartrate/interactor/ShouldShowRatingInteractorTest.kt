package com.g2pdev.smartrate.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.cache.SessionCountCache
import com.g2pdev.smartrate.interactor.is_rated.SetIsRated
import com.g2pdev.smartrate.interactor.last_prompt.SetLastPromptSessionToCurrent
import com.g2pdev.smartrate.interactor.never_ask.SetNeverAsk
import javax.inject.Inject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ShouldShowRatingInteractorTest : BaseTest() {

    @Inject
    lateinit var sessionCountCache: SessionCountCache

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
        createDaggerComponent()
            .inject(this)
    }

    @Test
    fun testSessionCountNotReached() {
        shouldShowRating
            .exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )
            .test()
            .assertValue(false)
    }

    @Test
    fun testSessionCountReached() {
        sessionCountCache
            .put(10)
            .test()
            .assertComplete()

        shouldShowRating
            .exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )
            .test()
            .assertValue(true)
    }

    @Test
    fun testMinSessionCountBetweenPromptsNotReached() {
        sessionCountCache
            .put(10)
            .test()
            .assertComplete()

        setLastPromptSessionToCurrent
            .exec()
            .test()
            .assertComplete()

        shouldShowRating
            .exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )
            .test()
            .assertValue(false)
    }

    @Test
    fun testMinSessionCountBetweenPromptsReached() {
        sessionCountCache
            .put(10)
            .test()
            .assertComplete()

        setLastPromptSessionToCurrent
            .exec()
            .test()
            .assertComplete()

        sessionCountCache
            .put(13)
            .test()
            .assertComplete()

        shouldShowRating
            .exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )
            .test()
            .assertValue(true)
    }

    @Test
    fun testNeverAsk() {
        sessionCountCache
            .put(100)
            .test()
            .assertComplete()

        setNeverAsk
            .exec(true)
            .test()
            .assertComplete()

        shouldShowRating
            .exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )
            .test()
            .assertValue(false)
    }

    @Test
    fun testIsRated() {
        sessionCountCache
            .put(100)
            .test()
            .assertComplete()

        setIsRated
            .exec(true)
            .test()
            .assertComplete()

        shouldShowRating
            .exec(
                minSessionCount = 10,
                minSessionCountBetweenPrompts = 3
            )
            .test()
            .assertValue(false)
    }
}
