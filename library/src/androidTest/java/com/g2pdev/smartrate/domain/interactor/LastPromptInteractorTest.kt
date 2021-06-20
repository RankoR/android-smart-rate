package com.g2pdev.smartrate.domain.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.data.preference.SessionCountPreference
import com.g2pdev.smartrate.domain.interactor.last_prompt.GetLastPromptSession
import com.g2pdev.smartrate.domain.interactor.last_prompt.SetLastPromptSessionToCurrent
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class LastPromptInteractorTest : BaseTest() {

    @Inject
    lateinit var getLastPromptSession: GetLastPromptSession

    @Inject
    lateinit var setLastPromptSessionToCurrent: SetLastPromptSessionToCurrent

    @Inject
    lateinit var sessionCountPreference: SessionCountPreference

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testLastPromptInteractorTest() {
        Assert.assertEquals(0, getLastPromptSession.exec())

        sessionCountPreference.put(10)

        runBlocking {
            setLastPromptSessionToCurrent.exec()

            Assert.assertEquals(10, getLastPromptSession.exec())
        }
    }
}
