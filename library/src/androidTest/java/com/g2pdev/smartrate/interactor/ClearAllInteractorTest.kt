package com.g2pdev.smartrate.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.interactor.is_rated.IsRated
import com.g2pdev.smartrate.interactor.is_rated.SetIsRated
import com.g2pdev.smartrate.interactor.last_prompt.GetLastPromptSession
import com.g2pdev.smartrate.interactor.last_prompt.SetLastPromptSessionToCurrent
import com.g2pdev.smartrate.interactor.never_ask.IsNeverAsk
import com.g2pdev.smartrate.interactor.never_ask.SetNeverAsk
import com.g2pdev.smartrate.interactor.session_count.GetSessionCount
import com.g2pdev.smartrate.interactor.session_count.IncrementSessionCount
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
internal class ClearAllInteractorTest : BaseTest() {

    @Inject
    lateinit var getSessionCount: GetSessionCount

    @Inject
    lateinit var incrementSessionCount: IncrementSessionCount

    @Inject
    lateinit var isRated: IsRated

    @Inject
    lateinit var setIsRated: SetIsRated

    @Inject
    lateinit var isNeverAsk: IsNeverAsk

    @Inject
    lateinit var setNeverAsk: SetNeverAsk

    @Inject
    lateinit var getLastPromptSession: GetLastPromptSession

    @Inject
    lateinit var setLastPromptSessionToCurrent: SetLastPromptSessionToCurrent

    @Inject
    lateinit var clearAll: ClearAll

    @Before
    fun setUp() {
        createDaggerComponent()
            .inject(this)
    }

    @Test
    fun testClearAll() {
        incrementSessionCount
            .exec()
            .test()
            .assertComplete()

        incrementSessionCount
            .exec()
            .test()
            .assertComplete()

        incrementSessionCount
            .exec()
            .test()
            .assertComplete()

        setIsRated
            .exec(true)
            .test()
            .assertComplete()

        setNeverAsk
            .exec(true)
            .test()
            .assertComplete()

        setLastPromptSessionToCurrent
            .exec()
            .test()
            .assertComplete()

        clearAll
            .exec()
            .test()
            .assertComplete()

        getSessionCount
            .exec()
            .test()
            .assertValue(0)

        isRated
            .exec()
            .test()
            .assertValue(false)

        isNeverAsk
            .exec()
            .test()
            .assertValue(false)

        getLastPromptSession
            .exec()
            .test()
            .assertValue(0)
    }

}