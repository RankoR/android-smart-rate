package com.g2pdev.smartrate.domain.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.domain.interactor.is_rated.IsRated
import com.g2pdev.smartrate.domain.interactor.is_rated.SetIsRated
import com.g2pdev.smartrate.domain.interactor.last_prompt.GetLastPromptSession
import com.g2pdev.smartrate.domain.interactor.last_prompt.SetLastPromptSessionToCurrent
import com.g2pdev.smartrate.domain.interactor.never_ask.IsNeverAsk
import com.g2pdev.smartrate.domain.interactor.never_ask.SetNeverAsk
import com.g2pdev.smartrate.domain.interactor.session_count.GetSessionCount
import com.g2pdev.smartrate.domain.interactor.session_count.IncrementSessionCount
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
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
        createDaggerComponent().inject(this)
    }

    @Test
    fun testClearAll() {
        runBlocking {
            incrementSessionCount.exec()
            incrementSessionCount.exec()
            incrementSessionCount.exec()

            setIsRated.exec(true)
            setNeverAsk.exec(true)
            setLastPromptSessionToCurrent.exec()

            clearAll.exec()

            Assert.assertEquals(0, getSessionCount.exec())
            Assert.assertFalse(isRated.exec())
            Assert.assertFalse(isNeverAsk.exec())
            Assert.assertEquals(0, getLastPromptSession.exec())
        }
    }
}
